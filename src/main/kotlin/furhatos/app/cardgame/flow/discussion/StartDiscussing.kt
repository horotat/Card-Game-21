package furhatos.app.cardgame.flow.discussion

import furhatos.app.cardgame.*
import furhatos.app.cardgame.game.GameTable
import furhatos.flow.kotlin.*
import furhatos.skills.FurhatAPI
import furhatos.util.Language

val StartDiscussing: State = state(Discussing) {
    onEntry {
        /*
        if(!isThisRoundTwo) {
            // Grounding: "By the way, You can touch the screen to move the cards. I don't have hands, you see."
            furhat.attendCheck(users.other)
            furhat.say(output.by_the_way_name)
            furhat.attendCheck(GameTable.centerLocation)
            furhat.say(output.explain_you_can_move_cards)
            furhat.attendCheck(users.current)
            furhat.say (output.no_hands_you_see)
        }
        */
        Furhat.param.endSilTimeout = endSilTimeoutDiscussion
        furhat.attendOneOrAll()
        furhat.askCheck(output.ask_where_to_start)
    }
}