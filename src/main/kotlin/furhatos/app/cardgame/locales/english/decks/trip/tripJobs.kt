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

val tripJobsDeck = deck {

    imgFolder = "jobs"
    name = "Jobs"
    unitLabel = " °skilled"

    input {
        singular += "profession"

        min_def += options("the most useless", "the least useful", "the least important") / options("job", "profession", "occupation", "person", "one", "")

        max_def += options("the most") / options("useful", "skillful", "suitable") / options(
            "person",
            "occupation",
            "job",
            "occupation",
            "one",
            ""
        )


        is_min_def += options("may be", "might be", "could be", "is") / min_def
        is_max_def += options("may be", "might be", "could be", "is") / max_def

        is_less_than += "is less useful than"
        is_less_than += "is not as appropriate as"
        is_less_than += "is less skilled than"
        is_less_than += "seems less useful for survival than"
        is_less_than += "seems less useful than"
        is_less_than += "seems to be less important than"

        is_more_than += "seems more useful than"
        is_more_than += "is more important than"
        is_more_than += "is more useful than"
        is_more_than += "is more skillful than"
        is_more_than += "can be more useful than"
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
            +"occupation"
        }
        min_def = {
            random {
                +"the least useful"
                +"the most useless"
                +"the least important"
            }
            random {
                +"job"
                +"occupation"
                +"skill"
                +"profession"
            }
            random {
                +"for survival"
                +"to run away from island"
                +""
            }
        }
        max_def = {
            random {
                +"the most useful"
                +"the best"
                +"the most crucial"
            }
            random {
                +"job"
                +"occupation"
                +"skill"
                +"profession"
            }
            random {
                +"for survival"
                +"to run away from island"
                +""
            }
        }
        is_min = {
            random {
                +"cannot help at all for our survival."
                +"is of no help for our run away."
                +"cannot do anything to run away from island."
                +"is probably the last person you want to go and look for!"
                +"is the least helpful for rescue."
            }
        }
        is_max = {
            random {
                +"is the best person for this."
                +"is the key occupation to look for."
                +"is the key to our rescue"
                +"is who we depend on the most for survival"
                +"is the most helpful for rescue."
            }
        }
        is_less_than = {
            random {
                +"is less crucial than"
                +"is not as important as"
                +"is not as essential as"
                +"is less helpful than"
            }
        }
        is_more_than = {
            random {
                +"is more useful than"
                +"is a better choice than"
                +"seems more crucial than"
                +"is more helpful than"
            }
        }
    }

    wordDiscussion()

    cards.add(spokesperson().apply {
        truth = 1

        image = "spokesperson1.jpg"

        argument_low += { +"The problem cannot be solved by just speaking." }

        argument_high += { +"a spokesman could talk for us against tribe people of the island if they want to attack us." }
    })

    cards.add(teacher().apply {
        truth = 2

        image = "teacher1.jpg"

        argument_low += { +"What can our English teacher do in a far away island except for singing English songs for us?" }

        argument_high += { +"our teacher is trustable and nice to us." }
    })

    cards.add(meteorologist().apply {
        truth = 3
        image = "meteorologist1.jpg"

        argument_low += { +"The hurricane is already past. The meteorologist could be useful before that." }

        argument_high += { +"The meteorologist can predict when we should get off the island." }
        argument_high += { +"a meteorologist can anticipate the events in the sea or air if we want to fly again."}
    })

    cards.add(engineer().apply {
        truth = 4
        image = "engineer1.jpg"

        argument_low += { +"an engineer cannot so so much job by hand. and we do not know him." }

        argument_high += { +"He could help us know how to fix the airplane again." }
    })

    cards.add(welder().apply {
        truth = 5
        image = "welder1.jpg"

        argument_low += { +"The welder does not have so much scientific knowledge." }

        argument_high += { +"If anyone, it will be the welder to connect and fix the wings of our airplane." }
        argument_high += { +"the welder has skills to make other tools like a ship to run out of the island as well." }
    })

}
