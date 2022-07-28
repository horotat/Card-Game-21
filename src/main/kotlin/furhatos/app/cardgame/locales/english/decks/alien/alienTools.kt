package furhatos.app.cardgame.locales.english.decks.alien

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

val alienToolsDeck = deck {

    imgFolder = "tools"
    name = "Tools"
    unitLabel = " °usefulness"

    input {
        singular += "tool"
        singular += "object"
        singular += "gift"
        singular += ""

        min_def += options("the most useless", "the least useful", "the worst", "") / options("object", "tool", "one", "gift", "")
        min_def += "the least important item"
        min_def += "the least important thing"
        min_def += "the least important"
        min_def += "the least peaceful"

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
        is_more_than += "seems better than"
        is_more_than += "is more important than"
        is_more_than += "is more useful than"
        is_more_than += "is better than"
        is_more_than += "is a better gift than"
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
            +"gift"
            +"object"
        }
        min_def = {
            random {
                +"the most useless"
                +"the worst"
                +"the least peaceful"
            }
            random {
                +"item"
                +"tool"
                +"gift"
                +"object"
            }
        }
        max_def = {
            random {
                +"the most useful"
                +"the most positive"
                +"the best"
                +"the most important"
                +"the greatest"
            }
            random {
                +"item"
                +"tool"
                +"gift"
                +"object"
            }
        }
        is_min = {
            random {
                +"is the least useful object."
                +"shows that we want war."
                +"is the most useless of all."
                +"is probably the worst tool for this."
            }
        }
        is_max = {
            random {
                +"is the best tool as our gift."
                +"is the most worthy item to give to the aliens."
                +"is the most useful among all"
                +"is the best to show we're friendly."
                +"is the most valuable gift."
            }
        }
        is_less_than = {
            random {
                +"is less useful than"
                +"is not as valuable as"
                +"is not as useful as"
                +"is less peaceful than"
                +"is not showing our problem as much as"
                +"isn't better than"
            }
        }

        is_more_than = {
            random {
                +"is"
                +"seems"
                +"seems to be"
            }
            random {
                +"better than"
                +"a better object"
                +"more peaceful"
                +"more valuable"
                +"a better gift"
            }
            random {
                +"than"
                +"compared to"
            }
        }
    }

    wordDiscussion()

    cards.add(mallet().apply {
        truth = 1
        image = "mallet2.jpg"

        argument_low += { +"A mallet is a symbol of kicking and crashing. It might show that we're aggressive." }
        argument_low += { +"A mallet is heavy. The aliens will thing we say they're heavy for our planet." }
        argument_low += { +"How could a mallet show ozone problem? plus aliens will think we're angry at them."}
        argument_low += { +"What if aliens kick our negotiator in the head with the mallet we give them?" }

        argument_high += { +"A mallet might show the aliens that we are strong, so they'll be scared of us." }
        argument_high += { +"A mallet could show the aliens we want some fixing." }
    })

    cards.add(knife().apply {
        truth = 2
        image = "knife2.jpg"

        argument_low += { +"Do you want the aliens to think we want to attack them? why would give them a knife." }
        argument_low += { +"With a knife they might think we want to cut their heads. Let's avoid knife." }
        argument_low += { +"A knife wouldn't show them our ozone problem." }
        argument_low += { +"Aliens might not have knife on their planet and not know what to do with a knife."}

        argument_high += { +"With a knife we can point to the ripped of ozone." }
        argument_high += { +"Our negotiator could threaten them with a knife to fix our problem." }
    })

    cards.add(funnel().apply {
        truth = 3
        image = "funnel2.jpg"

        argument_low += { +"What if the aliens don't like a funnel?" }
        argument_low += { +"How do we know that aliens drink water. They might not know what a funnel is."}
        argument_low += { +"the funnel is too simple for a gift to aliens." }

        argument_high += { +"The funnel has a circle shape like earth and a hole on top like hole in the ozone. Maybe it's a good symbol for our problem." }
        argument_high += { +"A funnel has the shape of a UFO. Maybe because it looks like their space-ship they would like it." }
    })

    cards.add(compass().apply {
        truth = 4
        image = "compass2.jpg"

        argument_low += { +"If we give them a compass they might think the problem is on the ground." }

        argument_high += { +"Compass is a symbol of exploration and discovery. We can show off our knowledge." }
        argument_high += { +"The compass can be a fancy gift that could make them happy." }
    })

    cards.add(altimeter().apply {
        truth = 5
        image = "altimeter2.jpg"

        argument_low += { +"The aliens might think we want them to leave by showing the height on the altimeter." }

        argument_high += { +"With an altimeter we can show at which height the ozone hole is."}
        argument_high += { +"The altimeter looks the most high tech among all. It might show aliens that we're advanced enough to make cool tech."}
        argument_high += { +"With an altimeter we could show the we mean ozone which is in a high height." }
        argument_high += { +"The altimeter has cool shape, it might make the aliens happy." }
    })

}
