package furhatos.app.cardgame.flow.intro

import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.Playing
import furhatos.app.cardgame.flow.discussion.Discussing
import furhatos.app.cardgame.flow.discussion.StartDiscussing
import furhatos.app.cardgame.game.*
import furhatos.flow.kotlin.IntentClassifierBuilder
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.nlu.Entity
import furhatos.nlu.Intent
import furhatos.util.Language

fun SetupGame(deck: Deck) = state(Playing) {

    onEntry {
        send(ActionClearTable())
        Game.selectDeck(deck)
        Entity.forgetAll()
        Intent.forgetAll()
        Opinions.forget()
        Card.forgetInitiatives()
        //startDialogLogging()
        send(ActionDealCards(Game.deckWithCards))
        // "In this game we are supposed to sort these animals based on how fast they can run"
        furhat.attendOneOrAll()

        furhat.say(Game.deck.output.purpose)

        furhat.attendCheck(GameTable.centerLocation)
        furhat.say(output.explain_order)

        furhat.say {
            random {
                +"I'm a native English speaker,"
                +"You might not know some of the cards,"
                +"Some of them seem to be hard words,"
                +""
            }
            random {
                +"let's read the cards first."
                +"let's read our cards first."
                +"allow me to pronounce them once."
                +"I could read them once for you."
                +"let's read them once."
            }
            random {
                +"you could"
                +"try to"
                +"maybe"
                +""
            }
            random {
                +"repeat"
                +"read"
            }
            random {
                +"them"
                +"the words"
                +""
            }
            random {
                +"after me."
                +"with me."
            }


            +delay(1000)
            for (card in Game.cardSet.currentOrder) {
                +attend(card.location)
                +card.name
                +delay(1400)
            }
        }

        startTime = System.currentTimeMillis()
        furhat.attendOneOrAll()
        goto(StartDiscussing)
    }

}