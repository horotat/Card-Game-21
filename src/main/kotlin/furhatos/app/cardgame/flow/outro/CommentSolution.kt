package furhatos.app.cardgame.flow.outro

import furhatos.app.cardgame.attendCheck
import furhatos.app.cardgame.attendOneOrAll
import furhatos.app.cardgame.game.Opinions
import furhatos.app.cardgame.flow.Playing
import furhatos.app.cardgame.flow.intro.SelectDeck
import furhatos.app.cardgame.output
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val CommentSolution: State = state(Playing) {
    onEntry {
        val furhatWasCorrect = Opinions.getCorrect()
        if (furhatWasCorrect.isNotEmpty()) {
            furhat.say {
                include(output.i_told_you_that)
                include(furhatWasCorrect[0].getExpression())
            }
            furhat.say(output.why_did_you_not_listen)
        } else {
            val furhatWasIncorrect = Opinions.getIncorrect()
            if (furhatWasIncorrect.isNotEmpty()) {
                furhat.say {
                    include(output.i_thought_that)
                    include(furhatWasIncorrect[0].getExpression())
                }
                furhat.say(output.i_should_have_listened)
            }
        }
        delay(3000)
        furhat.attendOneOrAll()
        furhat.say("Okay, let's continue with our next game")
        goto(SelectDeck)
    }
}