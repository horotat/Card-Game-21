package furhatos.app.cardgame.flow

import furhatos.app.cardgame.ActionPlayersUpdate
import furhatos.app.cardgame.SenseSpeechDetected
import furhatos.app.cardgame.attendCheck
import furhatos.app.cardgame.leftToRight
import furhatos.event.CustomEvent
import furhatos.event.Event
import furhatos.event.actions.ActionConfigRecognizer
import furhatos.event.actions.ActionListen
import furhatos.event.monitors.MonitorListenStart
import furhatos.event.senses.SenseSpeech
import furhatos.event.senses.SenseSpeechStart
import furhatos.flow.kotlin.*

fun FlowControlRunner.updateUsersOnGUI() {
    send(ActionPlayersUpdate(left = users.list.any {it.head.location.x < 0}, right = users.list.any {it.head.location.x > 0}))
}

val TrackUsers: State = state(null) {

    onUserEnter {
        updateUsersOnGUI()
    }

    onUserLeave {
        updateUsersOnGUI()
    }

    onEvent<ActionConfigRecognizer>(instant = true) {
        println("Recognizer primed with: " + it.phrases)
    }

    onEvent<MonitorListenStart> {
        furhat.ledStrip.solid(java.awt.Color.GRAY)
    }

    onEvent<SenseSpeech> {
        furhat.ledStrip.solid(java.awt.Color.BLACK)
    }

    onEvent<SenseSpeechStart>(instant=true) {
        if (users.count == 1) {
            raise(SenseSpeechDetected(user=users.list[0]))
        }
    }

    onEvent("SenseSpeechChannel", instant=true) {
        if (users.count > 1 && furhat.isListening) {
            raise(SenseSpeechDetected(user=users.leftToRight[it.getInteger("channel")]))
        }
    }

}