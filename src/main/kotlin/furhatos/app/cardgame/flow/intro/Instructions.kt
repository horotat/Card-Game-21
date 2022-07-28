package furhatos.app.cardgame.flow.intro


import furhatos.app.cardgame.flow.Playing
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location

val instructionPaper = Location(0.0, -200.0, 0.0)

val gameInstructions = utterance {
    +"Today we are going to play a sorting game together. There is no loser in this game and we are going to have fun together."
    +delay(200)
    +"This game has two game steps with two different stories. Each story has three decks of cards."
    +delay(100)
    +"When a new deck of cards shows up, we have three minutes to sort the cards together the best that we can. The goal is to sort as many correct cards as possible."
    +delay(200)
    +"Among the cards, if you see some that you don't know their meanings, you can ask me for it."
    +attend(instructionPaper)
    +"Now let's look at them together"
    +delay(3000)
    +"As you see, you can ask me to define a word for you,"
    +delay(100)
    +"to use the word in a sentence or give you an example of it."
    +delay(100)
    +"If you are not sure how to pronounce a word you can touch its card and ask me to pronounce it for you."
    +attend(users.random)
    +delay(100)
    +"Also here I'm your teammate, and I'd be happy if you ask my opinion as well."
    +attend(users.other)
    +"I'd like to make us score better in the game. However, remember that like you, I also could be wrong about the orders."
}
val InstructionsState = state(Playing) {

    onEntry {
        furhat.attendAll()
        furhat.say(gameInstructions)

        furhat.ask("Do you have any questions?")
    }

    onNoResponse {
        furhat.ask("Do you have any questions?")
    }

    onResponse<Yes> {  // ask: should I also put another intent like, "I have a question" here?
        furhat.say("Great! Ask your questions from Alireza before we start the first game.")
        goto(WaitState)
    }

    onResponse<No> {
        furhat.say("Amazing, then let's start with the first game!")
        goto(WaitState)
    }

    onResponse {
        furhat.ask("Do you have any questions?")
    }
}