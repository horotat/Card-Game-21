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

val tripShelterDeck = deck {

    imgFolder = "shelters"
    name = "Shelters"
    unitLabel = " °safety"

    input {
        singular += "shelter"

        min_def += options("the most dangerous", "the least safe", "the least appropriate", "the worst") / options("place to hide", "shelter", "place", "one", "")

        max_def += options("the") / options("safest", "best", "most appropriate") / options(
            "place",
            "place to hide",
            "shelter",
            "one",
            ""
        )


        is_min_def += options("may be", "might be", "could be", "is") / min_def
        is_max_def += options("may be", "might be", "could be", "is") / max_def

        is_less_than += "is less safe than"
        is_less_than += "is not as safe as"
        is_less_than += "is less appropriate than"
        is_less_than += "is more dangerous than"
        is_less_than += "seems less safe for survival than"
        is_less_than += "seems not as safe as"
        is_less_than += "seems to be less important than"

        is_more_than += "seems safer than"
        is_more_than += "is less dangerous than"
        is_more_than += "is more appropriate than"
        is_more_than += "is safer than"
        is_more_than += "can be safer than"
        is_more_than += "seems safer than"
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
            +"shelter"
        }
        min_def = {
            random {
                +"the most dangerous"
                +"the least reliable"
                +"the worst"
                +"the weakest"

            }
            random {
                +"place"
                +"location"
            }
            random {
                +"to hide"
                +"to take shelter in"
                +""
            }
        }
        max_def = {
            random {
                +"the best"
                +"the safest"
                +"the strongest"
                +"the firmest"
            }
            random {
                +"place"
                +"location"
            }
            random {
                +"to hide"
                +"to take shelter in"
                +""
            }
        }
        is_min = {
            random {
                +"cannot help at all for our survival."
                +"doesn't keep us safe at all."
                +"is the most dangerous place for a shelter."
                +"is the last place you might want to go to."
                +"is not even a shelter."
            }
        }
        is_max = {
            random {
                +"is the best place to take shelter in."
                +"is the strongest place"
                +"is the safest place among all."
                +"is where you're protected"
                +"is the most helpful for our rescue."
            }
        }
        is_less_than = {
            random {
                +"is more dangerous than"
                +"is not as safe as"
                +"is not as strong as"
                +"is less safe than"
                +"is weaker than"
                +"seems to be more dangerous than"
                +"doesn't seem as safe as"
                +"doesn't seem to be as firm as"
            }
        }
        is_more_than = {
            random {
                +"is stronger than"
                +"is safer than"
                +"seems to be stronger than"
                +"seems to be safer than"
                +"is better place to shelter compared to"
                +"keeps you safer than"
            }
        }
    }

    wordDiscussion()

    cards.add(hummock().apply {
        truth = 1
        image = "hummock1.jpg"

        argument_low += { +"Most probably the wind will blow you off since there is nothing to hang onto on a hummock." }
        argument_low += { +"a hummock is to low to make a good place to shelter."}

        argument_high += { +"there are not so many objects that can get up and hit you in a hummock." }
        argument_high += { +"there are not many things around on a hummock so if you fall on the ground you'll be safe."}
    })

    cards.add(tent().apply {
        truth = 2
        image = "tent1.png"

        argument_low += { +"The wind easily can crash and crumble your tent." }

        argument_high += { +"in a tent you can be safe of having small things around the air hitting your face." }
    })

    cards.add(fuselage().apply {
        truth = 3
        image = "fuselage1.jpg"

        argument_low += { +"The fuselage of your airplane is already broken. It might not be very strong to keep you safe from the wind." }

        argument_high += { +"The fuselage of the airplane is usually built very strong. It might be a good idea to hide here." }
    })

    cards.add(forest().apply {
        truth = 4
        image = "forest1.jpg"

        argument_low += { +"There are lots of trees and lots of sharp pieces of wood and branches hang from them. In the wind they might disattach and hit you in the head." }

        argument_high += { +"The trees are very good at stopping the strong winds." }
    })

    cards.add(escarpment().apply {
        truth = 5
        image = "escarpment1.jpg"

        argument_low += { +"you could get hit to the rocks under an escarpment." }

        argument_high += { +"below an escarpment wind cannot blow very hard." }
        argument_high += { +"there are not many small objects that can float around in the air and hit you beside the escarpment." }
    })
}
