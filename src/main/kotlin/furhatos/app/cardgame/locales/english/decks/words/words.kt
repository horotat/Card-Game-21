package furhatos.app.cardgame.locales.english.decks.words

import furhatos.app.cardgame.game.Card
import furhatos.app.cardgame.game.Deck

// Jobs
fun Deck.spokesperson() = Card(this).apply {
        id = "spokesperson"
        name = "Spokesperson"
        gramclass = "noun"

        def = "the spokesperson"
        indef = "a spokesperson"

        input += "spokesperson"
        input += "the spokesperson"

        definition += { +"A spokesperson, is someone who is chosen to speak officially for a group or organization." }
        definition += { +"A spokesperson, is a person who talks to media, for a government, and answers question." }
        definition += { +"A person, who represents a company, industry, or cause in the media, is called, the spokesperson." }

        example += { +"the meeting was attended by spokespersons for all the major branches of the government." }
        example += { +"How would you like to be the spokesperson for this center?” he asked me." }
        example += { +"the spokesperson of Sweden revealed the new regulations passed by the Swedish government."}
    }

fun Deck.teacher() = Card(this).apply {
        id = "teacher"
        name = "Teacher"
        gramclass = "noun"

        askForDefinition = false

        def = "the teacher"
        indef = "a teacher"

        input += "teacher"
        input += "the teacher"

        definition += { +"A teacher is a person whose occupation is teaching" }
        definition += { +"a teacher helps people to learn new things."}

        example += { +"our teacher gave us lots of assignments for the next week." }
        example += { +"my math teacher has very good methods to make me understand the topic."}
}

fun Deck.meteorologist() = Card(this).apply {
        id = "meteorologist"
        name = "Meteorologist"
        gramclass = "noun"

        def = "the meteorologist"
        indef = "a meteorologist "

        input += "meteorologist"
        input += "the meteorologist"
        input += "Metro urologists"

        definition += { +"The expert TV reporter, who presents the nightly weather forecast, is a meteorologist." }
        definition += { +"Meteorologist, is a specialist who studies processes in the earth's atmosphere, that cause weather conditions." }
        definition += { +"A meteorologist, is a person who studies the weather, and climate." }

        example += { +"a meteorologist predicted a hurricane for this weekend." }
        example += { +"I like the way this meteorologist explains the weather of the day on TV."}
}

fun Deck.engineer() = Card(this).apply {
        id = "engineer"
        name = "Engineer"
        gramclass = "noun"

        def = "the engineer"
        indef = "an engineer"

        input += "engineer"
        input += "the engineer"

        definition += { +"an engineer, is a person who uses scientific knowledge to solve practical problems." }
        definition += { +"an engineer, can design new tools, and fix problems, with their scientific knowledge."}

        example += { +"an engineer fixed our mobile phone last week." }
        example += { +"the telecommunication engineers in Sweden are working on a new mobile technology."}
        example += { +"we have an electrical engineer in our company to solve our energy waste issue."}
}

fun Deck.welder() = Card(this).apply {
        id = "welder"
        name = "Welder"
        gramclass = "noun"

        def = "the welder"
        indef = "a welder"

        input += "welder"
        input += "the welder"
        input += "swindle"
        input += "rainbow"
        input += "ramble"
        input += "Zelda"
        input += "vendor"

        definition += { +"A welder, joins pieces of metal, by heating them and pushing them together." }
        definition += { +"A welder, is a person whose job is to join pieces of metal together, by heating the edges, until they begin to melt, and then pressing them together." }
        definition += { +"A welder, is responsible for assembling pieces of metal together, or repairing damage in metal components." }

        example += { +"A welder fixed my broken bike last week by attaching two piece of metal to it." }
        example += { +"The welders did a lot of work sticking the Eiffel tower together." }
        example += { +"highly skilled welders work in airplane factory for connecting the wings."}
}


// Tools
fun Deck.funnel() = Card(this).apply {
        id = "funnel"
        name = "Funnel"
        gramclass = "noun"

        def = "the funnel"
        indef = "a funnel"

        input += "funnel"
        input += "the funnel"
        input += "a funnel"
        input += "Faneuil"
        input += "a Faneuil"
        input += "furlough"

        definition += { +"A funnel, has a wide opening, and narrow end, to pour liquids in small neck bottles." }
        definition += { +"The cone-shaped tool, you use to pour liquid, into a small hole, is a funnel." }
        definition += { +"A funnel has a large opening, and a small end, to put liquids, in small neck bottle."}

        example += { +"She put oil in the engine with a funnel." }
        example += { +"Use a funnel to pour the soda into a bottle." }
}

fun Deck.knife() = Card(this).apply {
        id = "knife"
        name = "Kitchen Knife"
        def = "the kitchen knife"
        indef = "a kitchen knife"
        gramclass = "noun"

        askForDefinition = false

        input += "kitchen knife"
        input += "the kitchen knife"

        definition += { +"A knife, is a sharp tool that's used for slicing or cutting. Usually made of metal." }
        definition += { +"A knife, is a tool to cut food and vegetable in the kitchen."}

        example += { +"He is cutting the apples with a knife." }
        example += { +"they knifed the zombie who attacked them." }
}

fun Deck.mallet() = Card(this).apply {
        id = "mallet"
        name = "Mallet"
        gramclass = "noun"

        def = "the mallet"
        indef = "a mallet"

        input += "mallet"
        input += "the mallet"
        input += "merit"
        input += "malice"
        input += "mellitus"
        input += "mullet"
        input += "mellow"

        definition += { +"Mallet, is type of a hammer, with a rounded head." }
        definition += { +"A mallet, is a hammer with a short handle, and a large head, shaped like a barrel." }
        definition += { +"A mallet, is one type of hammer." }

        example += { +"A mallet can be used to stabilize the nails of a tent." }
        example += { +"He used the mallet to hammer tent spikes into the ground." }
        example += { +"He hit the nail with his mallet." }
}

fun Deck.compass() = Card(this).apply {
        id = "compass"
        name = "Compass"
        gramclass = "noun"

        def = "the compass"
        indef = "compass"

        input += "compass"
        input += "the compass"

        definition += { +"Compass, is an instrument for showing direction. A typical compass has a moving magnetic needle that points north." }
        definition += { +"Compass, is a rounded shape magnetic device, that shows the direction of north, east, west, and south."}

        example += { +"Sailors relied on a compass to find their way across the ocean." }
        example += { +"I found my way out of the mountain with a compass." }
}

fun Deck.altimeter() = Card(this).apply {
        id = "altimeter"
        name = "Altimeter"
        gramclass = "noun"

        def = "the altimeter"
        indef = "an altimeter"

        input += "altimeter"
        input += "the altimeter"
        input += "alternator"
        input += "an alternator"
        input += "alma mater"
        input += "Alameda"
        input += "a elf m"
        input += "attempted murder"
        input += "1/2 m"
        input += "Martin Luther"

        definition += { +"An altimeter is a tool to measure height, from the ground."}
        definition += { +"Altimeter is a device, that measures height, above the Earth's surface." }
        definition += { +"An altimeter is a device, to measure the height, from the ground. also used in navigation."}

        example += { +"The altimeter tells how high the airplane is flying." }
        example += { +"According to the altimeter, they were nearly two thousand meters up." }
        example += { +"As it shows on my altimeter, we have climbed 200 meters of this hill."}
}


// Shelter
fun Deck.hummock() = Card(this).apply {
        id = "hummock"
        name = "Hummock"
        gramclass = "noun"

        def = "the hummock"
        indef = "a hummock"

        input += "hummock"
        input += "the hummock"
        input += "homophone"
        input += "humble"
        input += "hammock"
        input += "Houma"

        definition += { +"A hummock, is a short hill." }
        definition += { +"A short hill, is called, hummock."}
        definition += { +"Hummocks, are short hills, of about 15 meters height."}

        example += { +"They sat on the grass behind a low hummock, taking a break together." }
        example += { +"Look at that cute sheep on the hummock eating grass."}
}

fun Deck.tent() = Card(this).apply {
        id = "tent"
        name = "Tent"
        gramclass = "noun"

        askForDefinition = false

        def = "the tent"
        indef = "a tent"

        input += "tent"
        input += "the tent"

        definition += { +"A tent, is a portable shelter ,usually of canvas stretched over supporting poles." }
        definition += { +"A tent, is a temporary shelter, for camping." }

        example += { +"While camping we slept in the tent." }
        example += { +"We put up our tent in on the mountain and we rested in it."}
}

fun Deck.fuselage() = Card(this).apply {
        id = "fuselage"
        name = "Fuselage"
        gramclass = "noun"

        def = "the fuselage"
        indef = "a fuselage"

        input += "fuselage"
        input += "the fuselage"
        input += "Fossil watch"
        input += "fuchsia rash"
        input += "vassalage"
        input += "fusa lodge"

        definition += { +"The fuselage, is the main part of an airplane." }
        definition += { +"In the airplane, the part in which you sit as a passenger, is called the fuselage. Your luggage rides in the fuselage too." }
        definition += { +"The body of an airplane, is called fuselage."}

        example += { +"It had short wings and a fat, stubby fuselage with a huge engine in front." }
        example += { +"Wow! how can these wings fly such a huge fuselage."}
}

fun Deck.forest() = Card(this).apply {
        id = "forest"
        name = "Forest"
        gramclass = "noun"

        askForDefinition = false

        def = "the forest"
        indef = "a forest"

        input += "forest"
        input += "the forest"

        definition += { +"A forest, is a densely wooded area, or land covered with trees, and shrubs." }
        definition += { +"A forest, is the set of trees and other plants in a large densely wooded area." }

        example += { +"Much of Europe was covered with forest." }
        example += { +"This forest is full of trees." }
}

fun Deck.escarpment() = Card(this).apply {
        id = "escarpment"
        name = "Escarpment"
        gramclass = "noun"

        def = "the escarpment"
        indef = "an escarpment"

        input += "escarpment"
        input += "the escarpment"

        definition += { +"An escarpment, is like a mountain, but sharp." }
        definition += { +"The escarpment, is where the earth's height, suddenly changes. Like sharp hills, or natural walls." }
        definition += { +"an escarpment, is a tall natural wall. like a mountain, but going up suddenly, and sharply." }

        example += { +"To the northeast the rolling mountains ended in a steep escarpment." }
        example += { +"I saw in my mind escarpments rising into the clouds, a kind of natural Great Wall of China." }
}