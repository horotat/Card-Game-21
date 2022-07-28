package furhatos.app.cardgame

import furhatos.app.cardgame.flow.discussion.Discussing
import furhatos.app.cardgame.game.*
import furhatos.app.cardgame.gestures.GazeAversion
import furhatos.event.EventSystem
import furhatos.event.actions.ActionAttend
import furhatos.event.actions.ActionGaze
import furhatos.event.senses.SenseUserAttend
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.nlu.EnumItem
import furhatos.nlu.Response
import furhatos.records.Location
import furhatos.records.User
import furhatos.skills.UserManager
import java.time.LocalDateTime
import java.util.concurrent.TimeoutException
import furhatos.flow.kotlin.voice.PollyNeuralVoice.Matthew

// Global variables used in flow
// ===========================================================================

val sayFurhat = "<phoneme alphabet=\"ipa\" ph=\"fɜrr.hætt\">furhat</phoneme>" // pronounce "furhat" correctly ; works well with amazon-polly-voice, matthew-neural
val furhatInventionYear:Int = 2012
val currentYear:Int = LocalDateTime.now().year
val output get() = Game.locale.output
val input get() = Game.locale.input
val focusStack get() = Game.cardSet.focusStack
val cardsMovingDone = DelayedAction()
val cardsAttentionLimiter = RepetitionLimiter()
val backchannelLimiter = RepetitionLimiter()
//val gazeShiftLimiter = RepetitionLimiter()
var oldOrder: CardOrder? = null
var cardsReordered: SenseCardsReordered? = null
var currentResponse: Response<*>? = null

var conditionCharacterName = "Furhat"

var startTime = Long.MAX_VALUE

// Flags and Counters

private val counterMap = mutableMapOf<Int,Int>()
var cardsMoving = false
var hasCheckIfDoneBeenTriggered = false
var knowsHowToEndGame = false
var knowsToAskFurhatAboutCards = false
var knowsHowToMoveCards = false
//var roundCounter = 0
var onResponseCounterInDiscussingState = 0

var gameList = mutableListOf<Deck>()

fun resetFlagsAndCounters() {
    counterMap.clear()
    cardsMoving = false
    hasCheckIfDoneBeenTriggered = false
    knowsHowToEndGame = false
    knowsToAskFurhatAboutCards = false
    knowsHowToMoveCards = false
    currentResponse = null
    //roundCounter = 0
    onResponseCounterInDiscussingState = 0
}

// Util functions
// ===========================================================================

fun secondsPlayed() = (Math.max(0, (System.currentTimeMillis() - startTime) / 1000)).toInt()
fun secondsLeft() = (Math.max(0, maxGameTime - secondsPlayed()))

/**
 * Increments an integer bound to the provided keys.
 * Returns 1 the first time it is called for the provided keys.
 */
fun counter(vararg keys: Any) : Int {
    val key = keys.sumBy { it.hashCode() }
    counterMap[key] = counterMap.getOrDefault(key, 0) + 1
    return counterMap[key]!!
}


/**
 * Util method to ask and goto(By inheritance not goto()) Discussing state for response
 */
fun FlowControlRunner.discuss(utteranceBuilder: UtteranceDefinition) {
    goto(state(Discussing) {
        onEntry {
            furhat.askCheck(utteranceBuilder)
        }
    })
}

/**
 * Checks whether the user addressed Furhat
 */

/*
val Response<*>.isTowardsFurhat : Boolean get() {
    return UserManager.count < 2 || "system" == UserManager.getUser(userId).attending
}

object CardgameTurnTakingPolicy: TurnTakingPolicy {
    override fun turnYieldTimeout(response: Response<*>): Int {
        if (cardsMoving) {
            println("Cards moving")
            return 4000
        } else if (UserManager.list.any {
                it.isAttendingFurhat
            }) {
            return 0
        } else {
            return 4000
        }
    }
}
 */

fun utteranceDef() : UtteranceDefinition =  {}
fun utteranceArgDef() : (String)->UtteranceDefinition = {{}}
fun utteranceOptions() = GenericOptionList<UtteranceDefinition>()

fun options() : OptionList {
    return OptionList()
}

fun options(vararg item: String) : OptionList {
    return OptionList(item.toList())
}

fun List<Double>.stdev() : Double {
    val mean = this.average()
    var variance = 0.0
    for (pd in this) {
        variance += Math.pow(pd - mean, 2.0) / this.count()
    }
    return Math.sqrt(variance)
}

fun cumulativeStandardNormalDistribution(zInput: Double): Double {
    var z = zInput
    val neg = if (z < 0.0) 1 else 0
    if (neg == 1)
        z *= -1.0

    val k = 1.0 / (1.0 + 0.2316419 * z)
    var y = ((((1.330274429 * k - 1.821255978) * k + 1.781477937) * k - 0.356563782) * k + 0.319381530) * k
    y = 1.0 - 0.398942280401 * Math.exp(-0.5 * z * z) * y

    return (1.0 - neg) * y + neg * (1.0 - y)
}


fun SenseUserAttend.isTowardsFurhat(): Boolean {
    return UserManager.getUser(userId).isAttendingFurhat
}

fun FlowControlRunner.pauseToLookForAttendingUser(shortWait: Long = 400) {
    when {
        users.current.isAttendingFurhat() -> {
            furhat.attendCheck(users.current)
        }
        users.other.isAttendingFurhat() -> {
            furhat.attendCheck(users.other)
        }
        else -> {
            furhat.attendOneOrAll()
            delay(shortWait)
        }
    }
}

fun Furhat.sayOnce(utteranceDefinition: UtteranceDefinition) {
    if (counter(utteranceDefinition) == 1) {
        say(utteranceDefinition)
    }
}

var lastAttend = System.currentTimeMillis()
val delayedAttend = DelayedAction()


fun Furhat.attendOneOrAll() {
    if (users.count > 1)
        attendAll()
    else
        attendCheck(users.random)
}

fun Furhat.attendCheck(user: User) {
    delayedAttend.cancel()
    UserManager.current = user
    if (System.currentTimeMillis() - lastAttend > 2000) {
        EventSystem.send(ActionAttend.Builder().target(user.id).mode(ActionGaze.Mode.DEFAULT).buildEvent())
        lastAttend = System.currentTimeMillis()
    } else {
        EventSystem.send(ActionAttend.Builder().target(user.id).mode(ActionGaze.Mode.EYES).buildEvent())
        delayedAttend.run(2000) {
            EventSystem.send(ActionAttend.Builder().target(user.id).mode(ActionGaze.Mode.DEFAULT).buildEvent())
            lastAttend = System.currentTimeMillis()
        }
    }
}

fun Furhat.attendCheck(location: Location) {
    delayedAttend.cancel()
    if (System.currentTimeMillis() - lastAttend > 2000) {
        EventSystem.send(ActionAttend.Builder().location(location).mode(ActionGaze.Mode.DEFAULT).buildEvent())
        lastAttend = System.currentTimeMillis()
    } else {
        EventSystem.send(ActionAttend.Builder().location(location).mode(ActionGaze.Mode.EYES).buildEvent())
        delayedAttend.run(2000) {
            EventSystem.send(ActionAttend.Builder().location(location).mode(ActionGaze.Mode.DEFAULT).buildEvent())
            lastAttend = System.currentTimeMillis()
        }
    }
}

fun Furhat.attendNonDominant() {
    val nonDominant = users.nonDominant
    if (nonDominant != null) {
        attendCheck(nonDominant)
    } else {
        attendOneOrAll()
    }
}

fun Furhat.askCheck(utterance: Utterance) {
    runner.loadIntents()
    ask(utterance)
}

fun Furhat.askCheck(utteranceDefinition: UtteranceDefinition) {
    runner.loadIntents()
    ask(utteranceDefinition)
}

fun Furhat.listenCheck() {
    runner.loadIntents()
    listen()
}

private fun FlowControlRunner.loadIntents() {
    IntentClassifierBuilder.init(this)
    var first = true
    while (true) {
        try {
            IntentClassifierBuilder.getIntentClassifier(furhat.inputLanguages[0], 800)
            break
        } catch (e: TimeoutException) {
            if (first) {
                furhat.say {
                    random {
                        +"Let's see"
                        +"Let me think"
                    }
                }
                first = false
            }
            furhat.gesture(GazeAversion(1.0))
            delay(1000)
        }
    }
}

fun Furhat.setAdult() {
    conditionCharacterName = "Matthew"
    character = "Marty"
    voice = PollyNeuralVoice.Matthew().apply { style = PollyNeuralVoice.Style.Conversational }
}

fun Furhat.setChild() {
    conditionCharacterName = "Kevin"
    character = "Billy"
    voice = PollyNeuralVoice("Kevin")
}