package furhatos.app.cardgame.flow.intro

import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.Parent
import furhatos.app.cardgame.flow.updateUsersOnGUI
import furhatos.app.cardgame.gestures.Sleep
import furhatos.app.cardgame.gestures.WakeUp
import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.records.Location

val Sleeping: State = state(Parent) {

    onEntry {
        send(ActionClearTable())
        send(ActionShowButton(output.button_tap_to_play))
        updateUsersOnGUI()
        furhat.stopSpeaking()
        furhat.attendNobody()
        furhat.attendCheck(Location(0, 0, 1))
        furhat.setDefaultMicroexpression(blinking = false, eyeMovements = false, facialMovements = false)
        furhat.gesture(Sleep, priority = 1)
    }

    onEvent(SenseGameReset) {
    }

    onEvent<SenseSkillGUIConnected> {
    }

    onEvent(SenseButtonClick) {
        if (users.count > 0) {
            send(ActionHideButton())
            furhat.setDefaultMicroexpression()
            furhat.gesture(WakeUp, priority = 1, async = false)
            goto(Greeting)
        }
    }

}