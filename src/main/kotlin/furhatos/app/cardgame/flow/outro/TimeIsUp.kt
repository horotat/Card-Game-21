package furhatos.app.cardgame.flow.outro

import furhatos.app.cardgame.attendOneOrAll
import furhatos.app.cardgame.endSilTimeoutDiscussion
import furhatos.app.cardgame.endSilTimeoutGeneral
import furhatos.app.cardgame.startTime
import furhatos.flow.kotlin.*
import furhatos.util.AudioPlayer

val TimeIsUp = state {

        val myAudioPlayer = AudioPlayer(this::class.java.getResource("/audio/alarm.wav"))

    onEntry {
        startTime = Long.MAX_VALUE
        Furhat.param.endSilTimeout = endSilTimeoutGeneral

        myAudioPlayer.play(100)
        delay(2300)

        furhat.attendOneOrAll()
        furhat.say {
            random {
                +"Oh, time is up."
                +"Ok. time to check the results."
                +"Ah, it finished."
            }
        }
        goto(CheckSolution)
    }

}