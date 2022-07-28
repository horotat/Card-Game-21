package furhatos.app.cardgame.flow.intro

import furhatos.app.cardgame.flow.Playing
import furhatos.app.cardgame.gameList
import furhatos.app.cardgame.locales.english.decks.alien.alienJobsDeck
import furhatos.app.cardgame.locales.english.decks.alien.alienShelterDeck
import furhatos.app.cardgame.locales.english.decks.alien.alienToolsDeck
import furhatos.app.cardgame.locales.english.decks.animalSpeedDeckEnglish
import furhatos.app.cardgame.locales.english.decks.trip.tripJobsDeck
import furhatos.app.cardgame.locales.english.decks.trip.tripShelterDeck
import furhatos.app.cardgame.locales.english.decks.trip.tripToolsDeck
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val WaitState = state(Playing) {

    onEntry {
        furhat.attendNobody()
    }

    onButton("Play Animal speed") {
        gameList = mutableListOf()
        goto(SetupGame(animalSpeedDeckEnglish))
    }

    onButton("Play Trip Tools 1") {
        gameList = mutableListOf(tripToolsDeck, tripShelterDeck, tripJobsDeck)
        goto(SelectDeck)
    }

    onButton("Play Trip Shelter 2") {
        gameList = mutableListOf(tripShelterDeck, tripJobsDeck)
        goto(SelectDeck)
    }

    onButton("Play Trip Jobs 3") {
        gameList = mutableListOf(tripJobsDeck)
        goto(SelectDeck)
    }

    onButton("Play Alien Shelter 1") {
        gameList = mutableListOf(alienShelterDeck, alienJobsDeck, alienToolsDeck)
        goto(SelectDeck)
    }

    onButton("Play Alien Jobs 2") {
        gameList = mutableListOf(alienJobsDeck, alienToolsDeck)
        goto(SelectDeck)
    }

    onButton("Play Alien Tools 3") {
        gameList = mutableListOf(alienToolsDeck)
        goto(SelectDeck)
    }

}