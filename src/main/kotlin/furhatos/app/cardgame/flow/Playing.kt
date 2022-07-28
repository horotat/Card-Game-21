package furhatos.app.cardgame.flow

import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.intro.Sleeping
import furhatos.app.cardgame.flow.outro.CheckSolution
import furhatos.app.cardgame.game.Game
import furhatos.app.cardgame.gestures.GazeAversion
import furhatos.event.actions.ActionLipSync
import furhatos.event.monitors.MonitorSpeechStart
import furhatos.event.senses.SenseSpeechStart
import furhatos.flow.kotlin.*
import furhatos.gestures.ARKitParams
import furhatos.gestures.defineGesture
import furhatos.nlu.common.RequestRepeat
import furhatos.records.Record


val Playing: State = state(Parent) {

    onEvent(SenseCardsMoved, instant = true) { event ->
        //cardsMoving = true

        val record = event.getRecord("params")
        val cards = record.getList<Record>("cards")
        if (oldOrder == null)
            oldOrder = Game.cardSet.currentOrder

        cards.forEachIndexed { index, cardRecord ->
            val position = cardRecord.getRecord("position")
            // We have to compensate for the fact that the coordinates from the GUI refers to the top-left corner, and not the center of the card
            val x = position.getDouble("x") + 0.075
            val y = position.getDouble("y") + 0.2
            val card = Game.cardSet.cards[index]
            val oldLocation = card.location
            card.setScreenLocation(x, y)
            val newLocation = card.location
            if (oldLocation != newLocation) {
                if (!furhat.isSpeaking) {
                    // Only attend to cards when Furhat is not speaking
                    cardsAttentionLimiter.limitRepeat(500) {
                        raise(ActionAttendCard(card.id))
                    }
                }
            }
        }

        val newOrder = Game.cardSet.currentOrder

        if (newOrder.hasChanged(oldOrder!!)) {
            //println("CARDS REORDERED")
            cardsMoving = true
            cardsMovingDone.run(3000) {
                cardsMoving = false
                //println("CARD ORDER CHANGED: " + newOrder)
                raise(SenseCardsReordered(Game.cardSet.lastKnownOrder, newOrder))
                Game.cardSet.lastKnownOrder = newOrder
            }
            oldOrder = newOrder
        }

    }

    onEvent<SenseSpeechDetected>(instant = true) {
        furhat.attendCheck(it.user)
    }

    onEvent(SenseCardSelected, instant = true) {event ->
        val index = event.getRecord("params").getInteger("index")
        val card = Game.cardSet.cards[index]
        focusStack.prime(card)
        furhat.attendCheck(card.location)
    }

    onEvent(SenseButtonClick) {
        goto(CheckSolution)
    }

    onResponse<RequestRepeat> {
        furhat.askCheck(furhat.dialogHistory.utterances.last().toUtterance())
    }

    onEvent<ActionLipSync>(instant=true) {
        if (furhat.isAttendingUser && !furhat.isAttendingAll) {
            var silences = it.phones.phones.dropWhile { it.name == "_s" }.dropLastWhile { it.name == "_s" }
                .filter { it.name == "_s" }.toMutableList()
            if (silences.isNotEmpty()) {
                runThread(true) {
                    var last = 0.0f
                    while (silences.isNotEmpty()) {
                        val silence = silences.removeAt(0)
                        val sleepTime = (silence.start - 0.2) - last
                        val avertTime = 0.2 + (silence.end - silence.start)
                        if (sleepTime > 0.0) {
                            Thread.sleep((sleepTime * 1000.0).toLong())
                            furhat.gesture(GazeAversion(avertTime))
                        }
                        last = silence.end
                    }
                }
            }
        }
    }

    onUserLeave(instant = true) {
        //if (users.count == 0) {
        //    goto(Sleeping)
        //}
    }
}