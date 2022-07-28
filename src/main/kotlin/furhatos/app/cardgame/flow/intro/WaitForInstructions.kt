package furhatos.app.cardgame.flow.intro

import furhatos.app.cardgame.attendOneOrAll
import furhatos.app.cardgame.flow.Parent
import furhatos.app.cardgame.flow.Playing
import furhatos.app.cardgame.game.Deck
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

fun WaitForInstructions(deck: Deck) = state(Playing) {

    onEntry {
        furhat.attendOneOrAll()
        furhat.say {
            random {
                +"So, what is the next step"
                +"So, what is on the next card"
                +"So, please read the next card."
                +"Cool. May you read the next step please?"
                +"let's read the next step's card."
            }
        }
        furhat.listen()
    }

    onButton("Deal cards") {
        // todo: ask the other one who was not reading: "did you get it? or did you understand the task?"
        goto(SetupGame(deck))
    }

    onResponse {
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }

}


