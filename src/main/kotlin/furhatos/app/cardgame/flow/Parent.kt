package furhatos.app.cardgame.flow

import furhatos.app.cardgame.SenseGameReset
import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.intro.Sleeping
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.flow.kotlin.*

val Parent: State = state {

    onEvent<SenseSkillGUIConnected> {
        goto(Sleeping)
    }

    onButton("Reset Game") {
        logger.stopLogging()
        goto(Sleeping)
    }

    onButton("Fetch names", instant = true) {
        fetchNames()
    }

    onEvent(SenseGameReset) {
        logger.stopLogging()
        goto(Sleeping)
    }

    if (logger.logging) {
        onButton("Stop logging", color = Color.Red) {
            logger.stopLogging()
            goto(Sleeping)
        }
    } else {
        onButton("Start logging", color = Color.Green) {
            logger.startLogging()
            goto(Sleeping)
        }
    }

    onButton("Dump dialog", instant = true) {
        Furhat.dialogHistory.dump()
    }

    onButton("Switch user", instant = true) {
        furhat.attendCheck(users.other)
    }

    onButton("Set adult", instant = true) {
        furhat.setAdult()
    }

    onButton("Set child", instant = true) {
        furhat.setChild()
    }

}