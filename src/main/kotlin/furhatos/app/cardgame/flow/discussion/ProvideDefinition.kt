package furhatos.app.cardgame.flow.discussion

import furhatos.app.cardgame.*
import furhatos.app.cardgame.game.Card
import furhatos.app.cardgame.nlu.AgreeIntent
import furhatos.app.cardgame.nlu.DisagreeIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Maybe
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.RandomList
import kotlin.random.Random

fun ProvideDefinition(card: Card): State = state(Discussing) {
    onEntry {
        focusStack.prime(card)
        furhat.attendNonDominant()
        furhat.askCheck {
            random {
                +"Do you know"
                +"are you familiar with"
            }
            random {
                +"the definition of"
                +"the meaning of"
                +"what is"
            }
            +card.indef
        }
    }

    onResponse<Yes> {
        card.providedDefinition()
        furhat.say{
            random {
                +"Okay, great"
                +"cool."
                +"So do you think it's card is in the right place?"
                +"nice. could you remind me what it is?"
                +"okay, nice"
                +"fantastic. so what do you think about it's position?"
            }
        }
        goto(Discussing)
    }

    onResponse<No> {
        card.providedDefinition()
        furhat.say(card.definition.randomAvoidRepeat()!!)
        goto(Discussing)
    }
    onResponse {
        card.providedDefinition()
        furhat.say(card.definition.randomAvoidRepeat()!!)
        goto(Discussing)
    }

}