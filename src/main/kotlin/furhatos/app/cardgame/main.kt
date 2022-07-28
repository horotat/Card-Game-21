package furhatos.app.cardgame

import furhatos.app.cardgame.flow.*
import furhatos.app.cardgame.game.*
import furhatos.app.cardgame.locales.english.englishLocale
import furhatos.app.cardgame.nlu.DynamicPersonName
import furhatos.event.EventSystem
import furhatos.event.monitors.MonitorGaze
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier
import furhatos.nlu.Response
import furhatos.skills.HostedGUI
import furhatos.skills.UserManager
import java.io.File

class CardgameSkill : Skill() {

    val gui = HostedGUI("Game GUI", "assets/gui", port=12345)

    override fun start() {
        LogisticMultiIntentClassifier.setAsDefault()
        //DynamicPersonName.fetchNames()
        Furhat.cameraFeed.enable()
        fetchNames()
        Game.locale = englishLocale
        //Furhat.turnTakingPolicy = CardgameTurnTakingPolicy
        UserManager.attentionGainedThreshold = attentionGainedThreshold
        UserManager.attentionLostThreshold = attentionLostThreshold
        Furhat.param.noSpeechTimeout = noSpeechTimeout
        Furhat.param.endSilTimeout = endSilTimeoutGeneral
        val flow = Flow()
        flow.run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
