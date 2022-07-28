package furhatos.app.cardgame.flow.discussion

import furhatos.app.cardgame.*
import furhatos.app.cardgame.nlu.AgreeIntent
import furhatos.app.cardgame.nlu.DisagreeIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Maybe
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.RandomList
import kotlin.random.Random

private val randomList = RandomList(2)

/**
 * An open question about opinions from the users
 */
fun RequestOpinion(open: Boolean, force: Boolean): State = state(Discussing) {
    onEntry {
        if (force || 0 == randomList.next()) {
            val nonDominant = users.nonDominant
            if (nonDominant != null) {
                furhat.attendCheck(nonDominant)
                furhat.askCheck {
                    if (user.name != null) {
                        +"${user.name}, "
                    }
                    if (open) {
                        include(output.request_opinion_open)
                    } else {
                        include(output.request_opinion)
                    }
                }
            } else {
                furhat.attendOneOrAll()
                if (open) {
                    furhat.askCheck(output.request_opinion_open)
                } else {
                    furhat.askCheck(output.request_opinion)
                }
            }
        } else {
            goto(Discussing)
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
        discuss(output.request_opinion_accept)
    }

    onResponse<No> {
        if (users.count > 1) {
            furhat.attendCheck(users.other)
            discuss(output.request_opinion_other)
        } else {
            goto(Discussing)
        }
    }

}