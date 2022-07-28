package furhatos.app.cardgame.locales.english

import furhatos.app.cardgame.flow.RobotTurn
import furhatos.app.cardgame.flow.user
import furhatos.app.cardgame.focusStack
import furhatos.app.cardgame.game.Deck
import furhatos.app.cardgame.nlu.CardEntity
import furhatos.nlu.Intent
import furhatos.util.Language


fun Deck.wordDiscussion() {

    questions {

        class AskWhatIs() : Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return kotlin.collections.listOf(
                    "what is @card?",
                    "define @card",
                    "what does @card mean?",
                    "what is the definition of @card",
                    "What do you mean by @card ?",
                    "what is the meaning of @card ?",
                    "what does @card do?",
                    "could you tell us more about @card ?",
                    "@card meaning",
                    "meaning of @card",
                    "can you describe @card",
                    "describe @card",
                    "explain @card",
                    "could you explain what is @card",
                    "could you explain what @card is",
                    "What was @card",
                    "what was @card again",
                    "I don't know what is @card",
                    "I don't know what a @card is",
                    "do you know what a @card is",
                    "do you know what is @card"
                )
            }
        }

        class AskExample(): Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return kotlin.collections.listOf(
                    "can you use @card in a sentence?",
                    "can you use @card in context?",
                    "How do you use @card in a sentence?",
                    "give an example of @card",
                    "how to use @card in a sentence?",
                    "give me an example of @card",
                    "could you give me an example of the @card",
                    "can you give me an example of the word @card"
                )
            }
        }

        fun RobotTurn.userFollowUpDefinition() {
            user(furhatos.nlu.intent("tell me another definition", "define more", "give me another definition")) {
                val card = focusStack[0]
                robot {
                    include(card.definition.randomAvoidRepeat()!!)
                }
                userFollowUpDefinition()
            }
        }

        user<AskWhatIs> {
            val card = it.card!!.card!!
            card.providedDefinition()
            robot {
                +{ focusStack.prime(card) }
                include(card.definition.randomAvoidRepeat()!!)
            }
            userFollowUpDefinition()
        }

        fun RobotTurn.userFollowUpExample() {
            user(
                furhatos.nlu.intent(
                    "another example",
                    "can you tell me another sentence?",
                    "give me another instance",
                    "tell me another example",
                    "I want more examples",
                    "try another example",
                    "could you tell me another one?"
                )
            ) {
                val anotherExample = furhatos.app.cardgame.focusStack[0]
                robot {
                    include(anotherExample.example.randomAvoidRepeat()!!)
                }
                userFollowUpExample()
            }
        }

        user<AskExample> {
            val anExample = it.card!!.card!!
            robot {
                +{ furhatos.app.cardgame.focusStack.prime(anExample) }
                include(anExample.example.randomAvoidRepeat()!!)
            }
            userFollowUpExample()
        }

    }
}