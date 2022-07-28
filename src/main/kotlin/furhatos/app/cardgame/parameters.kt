package furhatos.app.cardgame

val groupCode = "End"

// The gaze angle for users (towards Furhat) to consider their attention as gained
val attentionGainedThreshold = 25.0
// The gaze angle for users (towards Furhat) to consider their attention as lost
val attentionLostThreshold = 30.0
// How much silence (no response) should Furhat accept before taking the turn
val noSpeechTimeout = 8000
// How much silence (after the user has spoken) should Furhat wait for before taking the turn, in general
val endSilTimeoutGeneral = 1800
// How much extra silence (after the user has spoken) should Furhat wait for before taking the turn, during discussion
val endSilTimeoutDiscussion = 2300
// How far from Furhat the center of the table (screen) is
val tableDistance = 0.5
// The size of the table (screen) in diagonal inches
val tableSize = 24.0
// Time allowed for each game (in seconds)
val maxGameTime = 180
