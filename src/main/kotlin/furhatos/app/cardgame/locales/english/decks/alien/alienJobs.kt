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

val alienJobsDeck = deck {

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
            +"job"
        }
        min_def = {
            random {
                +"the least skillful"
                +"the most useless"
                +"the least important"
            }
            random {
                +"job"
                +"occupation"
                +"profession"
            }
            random {
                +"for negotiation"
                +"to talk with the aliens"
                +""
            }
        }
        max_def = {
            random {
                +"the most useful"
                +"the best"
                +"the most helpful"
            }
            random {
                +"job"
                +"occupation"
                +"profession"
            }
            random {
                +"for talking to the aliens"
                +"to speak with the aliens"
                +""
            }
        }
        is_min = {
            random {
                +"cannot help at all for our negotiations."
                +"is of no help for our conversations with the aliens."
                +"is the last person we want to choose."
                +"is the least skilled for talking."
                +"is the least helpful for our negotiation with aliens."
                +"does not have any speaking skills."
            }
        }
        is_max = {
            random {
                +"is the best person for this."
                +"is the key occupation to look for."
                +"is the key to our negotiations."
                +"is who we depend on the most for talking to the aliens."
                +"is the one who has the best speaking skills."
            }
        }
        is_less_than = {
            random {
                +"is less crucial than"
                +"is not as important as"
                +"is not as essential as"
                +"is less helpful than"
                +"is less skilled than"
                +"is not as talkative as"
            }
        }
        is_more_than = {
            random {
                +"is more useful than"
                +"is a better choice than"
                +"seems more crucial than"
                +"is more helpful than"
                +"is more skilled than"
                +"is more talkative than"
                +"is a better speaker than"
                +"is a better negotiator than"
                +"has higher speaking skills than"
            }
        }
    }

    wordDiscussion()

    // cards
    cards.add(welder().apply {
        truth = 1
        image = "welder2.jpg"

        argument_low += { +"Welders are practical people. They are not trained as speakers." }
        argument_low += { +"A welder's skills have nothing to do with complicated conversations."}

        argument_high += { +"Welders connect metals. Maybe our welder can connect us with aliens as well." }
    })

    cards.add(engineer().apply {
        truth = 2
        image = "engineer2.jpg"

        argument_low += { +"An engineer is trained to solved the practical problems, but not with speaking." }
        argument_low += { +"Negotiation is not one of the skills engineers have."}
        argument_low += { +"Talking to aliens is not like designing a device. What could an engineer do?"}

        argument_high += { +"Engineers have university education, so they might be good for conversation." }
        argument_high += { +"Engineers solve complex practical problems in their job. So they might be good to prevent war with aliens."}
    })

    cards.add(meteorologist().apply {
        truth = 3
        image = "meteorologist2.jpg"

        argument_low += { +"Figuring out alien language is not like predicting weather. What can a meteorologist do for this?" }
        argument_low += { +"Meteorologists are wrong sometimes about the weather even. I wouldn't trust to put my life into the hands of a meteorologist." }

        argument_high += { +"A meteorologist might be trained to talk on TV, so maybe she can also talk to aliens." }
        argument_high += { +"Meteorologists know how to represent the weather. Maybe he can also represent us?" }
        argument_high += { +"A meteorologist knows how to convince us about the weather. Maybe he could also convince the aliens to help us."}
        argument_high += { +"Meteorologists are educated and know how to tell about our ozone problem to the aliens." }
    })

    cards.add(teacher().apply {
        truth = 4
        image = "teacher2.jpg"

        argument_low += { +"A school teacher talks to kids not to aliens." }
        argument_low += { +"a school teacher might be scared to talk to aliens." }

        argument_high += { +"Teachers are good speakers." }
        argument_high += { +"Teachers are concerned about us. They will defend us against aliens." }
        argument_high += {
            random {
                +"Teachers are generally good in speaking."
                +"Teachers are good speakers."
                +"Teachers know how to speak well."
            }
        }
        argument_high += { +"Teachers know how to be calm and talk nicely." }
        argument_high += { +"Teachers are usually educated and can handle conflicts." }
    })

    cards.add(spokesperson().apply {
        truth = 5
        image = "spokesperson2.jpg"

        argument_low += { +"Spokesperson is not physically so strong." }
        argument_low += { +"the spokesperson is trained to talk for the governments not to the aliens."}

        argument_high += { +"A spokesperson is trained to do conversations." }
        argument_high += { +"A spokesperson is skilled with make speech." }
        argument_high += { +"The spokesperson has the skill sets for negotiation."}
        argument_high += { +"The spokesperson has education in speaking and representing our views."}
    })
}
