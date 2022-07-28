package furhatos.app.cardgame.locales.english.decks.trip

import furhatos.app.cardgame.flow.Nomatch
import furhatos.app.cardgame.flow.RobotTurn
import furhatos.app.cardgame.flow.discussion.TakeInitiative
import furhatos.app.cardgame.flow.user
import furhatos.app.cardgame.focusStack
import furhatos.app.cardgame.game.deck
import furhatos.app.cardgame.locales.english.decks.words.*
import furhatos.app.cardgame.locales.english.englishLocale
import furhatos.app.cardgame.locales.english.wordDiscussion
import furhatos.app.cardgame.nlu.CardEntity
import furhatos.app.cardgame.options
import furhatos.gestures.Gestures
import furhatos.nlu.Intent
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.nlu.intent
import furhatos.util.Language

val tripToolsDeck = deck {

    imgFolder = "tools"
    name = "Tools"
    unitLabel = " °usefulness"

    input {
        singular += "tool"

        min_def += options("the most useless", "the least useful") / options("tool", "one", "")
        min_def += "the least important item"
        min_def += "the least important thing"
        min_def += "the least important"

        max_def += options("the") / options("most useful") / options(
            "tool",
            "one",
            ""
        )
        max_def += "the most important item"
        max_def += "the most important thing"
        max_def += "the most important"


        is_min_def += options("may be", "might be", "could be", "is") / min_def
        is_max_def += options("may be", "might be", "could be", "is") / max_def

        is_less_than += "is less useful than"
        is_less_than += "is not as useful as"
        is_less_than += "seems less useful than"
        is_less_than += "seems to be less important than"

        is_more_than += "seems more useful than"
        is_more_than += "is more important than"
        is_more_than += "is more useful than"
        is_more_than += "is more handy than"
    }

    output {
        purpose = {
            random {
                +"Thank you for reading it."
                +"Nice reading skills."
                +"You've got a nice voice, thanks."
                +"Thanks."
                +""
            }
            random {
                +"I got the story."
                +"Yep I got it."
                +"sounds cool."
                +"Seems cool."
            }
            random {
                +"Let's see the cards."
                +"Ok, let's see what do we have."
                +"So these are our cards."
                +"Aha, there we go the cards."
                +"Ok. So we have these cards."
                +"Let's see."
                +"There we go."
            }
        }
        singular = {
            +"tool"
        }
        min_def = {
            random {
                +"the least useful"
                +"the most useless"
            }
            random {
                +"item"
                +"tool"
            }
        }
        max_def = {
            random {
                +"the most useful"
                +"the most handy"
                +"the most important"
            }
            random {
                +"item"
                +"tool"
            }
        }
        is_min = {
            random {
                +"is the least useful tool."
                +"seems to be the tool of no use."
                +"is the most useless of all."
                +"is probably the last tool you might want to take with you."
            }
        }
        is_max = {
            random {
                +"is the best tool you can take with you."
                +"is the most worthy item to take."
                +"is the most useful among all"
            }
        }
        is_less_than = {
            random{
                +"is less useful than"
                +"is not as handy as"
                +"is not as useful as"
            }
        }
        is_more_than = {
            random {
                +"is more useful than"
                +"is a better item than"
                +"seems more useful than"
                +"is handier than"
            }
        }
    }

    wordDiscussion()

    cards.add(funnel().apply {
        truth = 1
        image = "funnel1.jpg"

        argument_low += { +"A funnel has a bad shape to fit in your backpack." }
        argument_low += { +"A funnel doesn't seem like something you would need. Since you can carefully pour your liquids into your bottles." }

        argument_high += { +"During camping it's important to not waste your water. you can use a funnel to share your water into your friends bottle without wasting it." }
        argument_high += { +"You can use a funnel to make your voice louder when you shout." }
    })

    cards.add(knife().apply {
        truth = 2
        image = "knife1.jpg"

        argument_low += { +"You can use your teeth to cut your food, so that you save space for other things in your backpack." }
        argument_low += { +"knives can be very sharp. So it might be dangerous to carry them around." }
        argument_low += { +"Your teachers probably take a knife, so you might not need it that much." }
        argument_low += { +"the kitchen knife is big and takes up space."}

        argument_high += { +"You can use the knife as a weapon if a bear attacks you in the mountains." }
        argument_high += { +"you can use the knife to cut the climbing ropes in case you get stuck." }
        argument_high += { +"you can cut your fruit and eat your food comfortably with a knife." }
    })

    cards.add(mallet().apply {
        truth = 3
        image = "mallet1.jpg"

        argument_low += { +"A mallet can be heavy to carry around." }
        argument_low += { +"It's uncomfortable to fit a mallet in your bag." }
        argument_low += { +"you might not use a mallet that often during your camping."}

        argument_high += { +"a Mallet is perfect to stabilize your tent's nails or spikes." }
        argument_high += { +"You can use a mallet as a weapon against wild animals." }
        argument_high += { +"if your climbing nail does not stick into the rocks you can use the mallet." }
    })

    cards.add(compass().apply {
        truth = 4
        image = "compass1.jpg"

        argument_low += { +"everyone has GPS on their phone these days. What's the use of a compass?" }
        argument_low += { +"a compass could be broken." }

        argument_high += { +"even if your phone dies you could use a compass which doesn't need a battery."}
        argument_high += { +"Maybe the compass could be used for signalling somehow." }
        argument_high += { +"We could use the compass to make sure we don't walk in circles" }
        argument_high += { +"Using a compass you can find your way if your get lost." }
        argument_high += { +"it is small and does not take so much space. So why not take it?"}
    })

    cards.add(altimeter().apply {
        truth = 5
        image = "altimeter1.jpg"

        argument_low += { +"you don't need an altimeter because you already know how high the mountains are that you are going to climb." }
        argument_low += { +"You could search on google the height of places you are going to so you don't need an altimeter." }

        argument_high += { +"it's very important to know the height in the mountain since it defines the air pressure. you could use the altimeter for that." }
        argument_high += { +"if you wanna contact the mountain emergency, you could give them your precise altitude with an altimeter. "}
        argument_high += {
            random {
                +"you can measure the amount of your climb with an altimeter."
                +"you can measure how high you've climbed with an altimeter."
            }
        }
        argument_high += { +"An altimeter is also used in navigation." }
    })

}
