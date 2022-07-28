package furhatos.app.cardgame.flow.intro

import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.Playing
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val Greeting = state(Playing) {
    onEntry {
        resetFlagsAndCounters()
        //users.all.forEach {it.name = null}
        users.all.forEach {it.responses = 0}
        furhat.attendOneOrAll()
        furhat.say(output.greeting)
        furhat.attendCheck(users.random)
        goto(AskName())
    }
}