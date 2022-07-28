package furhatos.app.cardgame.flow.discussion

import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.Playing
import furhatos.app.cardgame.flow.outro.TimeIsUp
import furhatos.app.cardgame.game.Game
import furhatos.app.cardgame.game.Opinions
import furhatos.app.cardgame.nlu.*
import furhatos.event.senses.SenseUserAttend
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent
import furhatos.skills.UserManager

val Discussing = state(Playing) {

    onEntry {
        furhat.listenCheck()
    }

    onEvent<ActionAttendCard>(instant = true) {
        val card = Game.cardSet.getCard(it.cardId)
        if (card != null) {
            focusStack.prime(card)
            furhat.attendCheck(card.location)
        }
    }

    onResponse(priority = true) {
        // keep count of users turns so far
        onResponseCounterInDiscussingState++
        users.current.responses++
        //furhat.attendCheck(users.current)
        // Centering theory says that the first item is more likely to be in focus
        it.findAll(CardEntity()).reversed().forEach {
            focusStack.prime(it.card!!)
        }
        if (users.current.isAttendingFurhat && !cardsMoving) {
            propagate()
        } else {
            currentResponse = it
            // make a backchannel and continue listening
            backchannelLimiter.limitRepeat(10000) {
                furhat.say(output.backchannel)
            }
            furhat.listenCheck()
        }
    }

    onResponse<PronounceThis> {
        val card = focusStack[0]
        furhat.askCheck{
            random {
                +"Sure, it's pronounced as, "
                +"It's, "
                +"It is, "
            }
            +card.name
        }
    }

    onResponse<pronounceAllCards> {
        furhat.askCheck {
            random {
                +"sure!"
                +"You got it!"
            }
            random {
                +"They are: "
                +"From left to right: "
            }
            +delay(800)
            for (card in Game.cardSet.currentOrder) {
                +attend(card.location)
                +card.name
                +delay(800)
            }
            +attend(UserManager.current)
        }
    }

    // "Do you think the zebra is faster than the lion?"
    // "I think the zebra is faster than the lion"
    onResponse<CompareCardsIntent> {
        knowsToAskFurhatAboutCards = true
        var card1 = it.intent.card1?.card
        var card2 = it.intent.card2?.card //?:focusStack[0]
        // If only one card was mentioned, make sure that it becomes card1, and pick card2 from the focus stack
        if (card1 == null) {
            if (card2 == null) {
                card1 = focusStack[0]
            } else {
                card1 = card2
                card2 = null
            }
        }
        if (card2 == null) {
            card2 = focusStack.find { it.id != card1.id }
        }
        goto(CompareCards(Opinions.getComparison(card1, card2!!), card1))
    }

    // "Which one do you think is the fastest?"
    // "Do you think the zebra is the fastest?"
    // "I think the zebra is the fastest animal"
    onResponse<HighestCardIntent> {
        knowsToAskFurhatAboutCards = true
        goto(ExpressExtreme(true))
    }

    onResponse<LowestCardIntent> {
        knowsToAskFurhatAboutCards = true
        goto(ExpressExtreme(false))
    }

    // "What do you think about the zebra?"
    onResponse<CommentCardIntent> {
        knowsToAskFurhatAboutCards = true
        val card = it.intent.card?.card?:focusStack[0]
        goto(ExpressOpinion(card))
    }

    // "what do you think?"
    onResponse<RequestOpinionIntent> {
        goto(TakeInitiative())
    }

    // "why do you think so?"
    onResponse<RequestWhyIntent> {
        goto(MotivateOpinion)
    }

    onEvent<SenseCardsReordered>(instant = true) {
        knowsHowToMoveCards = true
        cardsReordered = it
        if (currentResponse != null) {
            raise(currentResponse!!)
        } else if (!furhat.isSpeaking && !users.areSpeaking) {
            goto(CommentReordered(it))
        }
    }

    onEvent<SenseUserAttend>(instant = true) {
        if (furhat.isListening && it.isTowardsFurhat() && currentResponse != null) {
            raise(currentResponse!!)
        }
    }

    onTime(repeat = 2000..2000, instant = true) {
        if (secondsLeft() <= 0) {
            raise("TimeIsUp")
        }
    }

    onEvent("TimeIsUp") {
        goto(TimeIsUp)
    }

    onButton("Check Solution") {
        goto(TimeIsUp)
    }

    // "We are done"
    onResponse<GameDoneIntent> {
        if (secondsLeft() <= 20) {
            goto(TimeIsUp)
        } else {
            val secLeft = secondsLeft()
            furhat.attendOneOrAll()
            furhat.ask("You still have $secLeft seconds left, let's try a bit more")
        }
    }

    // General questions
    Game.locale.questions?.invoke(this)

    // Questions regarding the deck
    Game.deck.questions?.invoke(this)

    onResponse(NullIntent) {
        if (cardsReordered != null) {
            goto(CommentReordered(cardsReordered!!))
        } else if (it.speech.length > 2.5) {
            // take the initative
            goto(TakeInitiative(it))
        } else {
            goto(thisState)
        }
    }

    onNoResponse {
        if (cardsMoving) {
            // Cards are moving, wait a bit more
            furhat.listenCheck()
        } else {
            // Users are silent and cards have not been moved recently, take the initative
            furhat.say(output.hold_floor)
            goto(TakeInitiative())
        }
    }

    onExit(inherit = true) {
        cardsReordered = null
        currentResponse = null
    }
}
