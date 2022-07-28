package furhatos.app.cardgame.flow.discussion

import furhatos.app.cardgame.*
import furhatos.app.cardgame.game.Opinion
import furhatos.app.cardgame.nlu.AgreeIntent
import furhatos.app.cardgame.nlu.DisagreeIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Maybe
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.RandomList

private val randomList = RandomList(2)

/**
 * Check whether the users agree with Furhat's opinion
 */
fun SeekAgreement(opinion: Opinion): State = state(Discussing) {
    onEntry {
        when (randomList.next()) {
            0 -> {
                val nonDominant = users.nonDominant
                if (nonDominant != null) {
                    furhat.attendCheck(nonDominant)
                    furhat.askCheck(output.seek_agreement_name)
                } else {
                    furhat.attendOneOrAll()
                    furhat.askCheck(output.seek_agreement)
                }
            }
            else -> {
                goto(Discussing)
            }
        }
    }

    onResponse<AgreeIntent> {
        raise(it, Yes())
    }

    onResponse<DisagreeIntent> {
        raise(it, No())
    }

    onResponse<DontKnow> {
        furhat.say(output.request_opinion_unsure)
        goto(TakeInitiative())
    }

    onResponse<Maybe> {
        furhat.say(output.request_opinion_unsure)
        goto(TakeInitiative())
    }

    onResponse<Yes> {
        opinion.agreement += users.current.id
        furhat.say(output.seek_agreement_accepted)
        delay(1000)
        if (opinion.needChange) {
            discuss(output.request_move_card)
        } else {
            if (cardsReordered != null)
                goto(CommentReordered(cardsReordered!!))
            else
                goto(Discussing)
        }
    }

    onResponse<No> {
        opinion.disagreement += users.current.id
        discuss(output.seek_agreement_rejected)
    }
}