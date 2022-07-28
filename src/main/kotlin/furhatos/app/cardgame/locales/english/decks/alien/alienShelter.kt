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

val alienShelterDeck = deck {

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
                +"puts us in complete exposure to sun."
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
                +"is more open than"
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
                +"has more shadows than"
            }
        }
    }

    wordDiscussion()

    cards.add(hummock().apply {
        truth = 1
        image = "hummock1.jpg"

        argument_low += { +"A hummock is completely open and we will be burnt under sun." }

        argument_high += { +"On a hummock we could freely run around." }
    })

    cards.add(forest().apply {
        truth = 2
        image = "forest2.jpg"

        argument_low += { +"In the forest there are lots of places that sun can reach us." }

        argument_high += { +"We might be able to hide below a tree." }
    })

    cards.add(escarpment().apply {
        truth = 3
        image = "escarpment2.jpg"

        argument_low += { +"based on the escarpments direction we might not be in the shadow." }

        argument_high += { +"escarpments can make very big shadows" }
        argument_high += { +"the rocks of escarpment can protect us from sun light." }
    })

    cards.add(tent().apply {
        truth = 4
        image = "tent2.png"

        argument_low += { +"tent is very small and it might become too hot inside." }

        argument_high += { +"you can quickly take the tent wherever you want."}
        argument_high += { +"some tents are build of protective materials." }
    })

    cards.add(fuselage().apply {
        truth = 5
        image = "fuselage2.jpg"

        argument_low += { +"fuselage might be already occupied by other people." }
        argument_low += { +"fuselage is small for 100 people and we might not all fit in there." }
        argument_low += { +"fuselage of a broken airplane might not be strong enough."}

        argument_high += { +"fuselage has lots of protective layers." }
        argument_high += { +"fuselage of an airplane is usually built with very strong materials to protec us from solar flairs."}
        argument_high += { +"The fuselage of the airplane is usually built very strong. It might be a good idea to hide here." }
    })

}
