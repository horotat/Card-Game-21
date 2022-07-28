package furhatos.app.cardgame.flow

import furhatos.app.cardgame.setAdult
import furhatos.autobehavior.enableSmileBack
import furhatos.event.monitors.MonitorListenStart
import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val Init = state(Parent) {
    onEntry {
        //flowLogger.start(File("flow.txt"))
        furhat.setAdult()
        furhat.enableSmileBack = true

        parallel(abortOnExit = false) {
            goto(TrackUsers)
        }
    }
}