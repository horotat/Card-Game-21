package furhatos.app.cardgame.nlu

import furhatos.app.cardgame.game.Card
import furhatos.app.cardgame.game.Game
import furhatos.app.cardgame.game.Deck
import furhatos.app.cardgame.input
import furhatos.nlu.*
import furhatos.nlu.common.PersonName
import furhatos.util.CommonUtils
import furhatos.util.Language
import org.apache.commons.csv.CSVFormat
import java.io.InputStreamReader
import java.net.URL
import kotlin.concurrent.thread

class RequestOpinionIntent : SimpleIntent(input.request_opinion)

class RequestWhyIntent : SimpleIntent(input.request_why)

class pronounceAllCards: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("can you pronounce all the cards?", "pronounce all the cards", "pronounce the cards", "read the cards", "read aloud the cards", "can you pronounce every word again", "could you pronounce all of the words")
    }
}

class PronounceThis: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("pronounce this", "how is it pronounced?", "read this", "say this", "how to say this?")
    }
}

class GameDoneIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return input.game_done
    }
}

class GameNotDoneIntent : SimpleIntent(input.game_not_done)

class AgreeIntent : SimpleIntent(input.agree)

class DisagreeIntent : SimpleIntent(input.disagree)

class TellNameIntent : Intent() {
    var name: DynamicPersonName? = null

    override fun getExamples(lang: Language): List<String> {
        return Game.locale.input.tell_name
    }
}

class DeckEntity(var deck: Deck? = null) : Entity() {
    override fun getInterpreterImpl(lang: Language): EntityInterpreter {
        return EnumInterpreter(getEnumItems(), lang, true, true)
    }
    private fun getEnumItems(): List<EnumItem> {
        return Game.decks.map { EnumItem(DeckEntity(it), it.name) }
    }
    override fun getSpeechRecPhrases(lang:Language): Collection<String> {
        return (getInterpreter(lang) as EnumInterpreter).getSpeechRecPhrases()
    }
}

class AskAboutDeckIntent: Intent() {
    var deck: DeckEntity? = null

    override fun getExamples(lang: Language): List<String> {
        return Game.locale.input.ask_about_deck
    }
}

/**
 *  "do you think the zebra is slower than the elk"
 *  "do you think it is slower than the elk"
 */
class CompareCardsIntent : Intent() {
    var card1: CardEntity? = null
    var card2: CardEntity? = null

    override fun getExamples(lang: Language): List<String> {
        return Game.locale.input.ask_compare_cards + Game.locale.input.assert_compare_cards
    }

    //override fun getSpeechRecPhrases(lang: Language?): List<String> {
    //    return Game.cardSet.cards.flatMap { it.input }
   // }
}

/**
 *  "which one do you think is the fastest"
 *  "do you think the zebra is the fastest one"
 */
class HighestCardIntent : Intent() {
    var card: CardEntity? = null

    override fun getExamples(lang: Language): List<String> {
        return Game.locale.input.ask_highest_card
    }
}

/**
 *  "which one do you think is the slowest"
 *  "do you think the zebra is the slowest one"
 */
class LowestCardIntent : Intent() {
    var card: CardEntity? = null

    override fun getExamples(lang: Language): List<String> {
        return Game.locale.input.ask_lowest_card
    }
}

/**
 *  "what do you think about the zebra"
 */
class CommentCardIntent : Intent() {
    var card: CardEntity? = null

    override fun getConfidenceThreshold(): Double {
        return 0.0
    }

    override fun getExamples(lang: Language): List<String> {
        return Game.locale.input.comment_card
    }
}

class CardEntity(var card: Card? = null) : Entity() {
    override fun getInterpreterImpl(lang: Language): EntityInterpreter {
        return EnumInterpreter(getEnumItems(), lang, true, true)
    }
    private fun getEnumItems(): List<EnumItem> {
        return Game.cardSet.cards.flatMap { card -> card.input.map { keyword -> EnumItem(CardEntity(card), keyword) }}
    }
    override fun getSpeechRecPhrases(lang:Language): Collection<String> {
        return (getInterpreter(lang) as EnumInterpreter).getSpeechRecPhrases()
    }
}

/**
 * Special name class where we fetch names from a Google Sheet, so that we can easily add names
 */
class DynamicPersonName(): EnumEntity(false, true){

    companion object {
        private var names: List<EnumItem>? = null
        private var fetchThread: Thread? = null
        @Synchronized
        fun fetchNames(millis: Long = 0) {
            if (fetchThread == null) {
                fetchThread = thread {
                    try {
                        val formUrl =
                                "https://docs.google.com/spreadsheets/d/1hoxpe4Ib_OD33xJPs4NLXq4NJrtNeu73OK5J7ahpQlk/gviz/tq?tqx=out:csv"
                        val records =
                                CSVFormat.EXCEL.parse(InputStreamReader(URL(formUrl).openStream()))
                        names = records.mapNotNull { column ->
                            if (column.size() > 0) {
                                val first = column[0].trim()
                                if (first.isNotEmpty()) {
                                    val synonyms = column.map { it.trim() }.filter(String::isNotEmpty)
                                    val prototype = DynamicPersonName().apply { value = first }
                                    synonyms.map { EnumItem(prototype, it, Language.ENGLISH_US) }
                                } else null
                            } else null
                        }.flatten()
                    } catch (e: Exception) {
                        CommonUtils.getLogger(this::class.java).warn("Couldn't fetch person names from Google Sheet")
                    }
                }
            }
            if (millis > 0 && fetchThread?.isAlive == true) {
                fetchThread?.join(millis)
            }
        }
    }

    override fun getEnumItems(lang: Language): List<EnumItem> {
        fetchNames(1000)
        return if (names != null) {
            names!!
        } else {
            CommonUtils.getLogger(this::class.java).warn("Using default PersonNames")
            PersonName().getEnumItems(lang).map { EnumItem(DynamicPersonName().apply { value = (it.entity as PersonName).value }, it.wordString) }
        }
    }

}
