package furhatos.app.cardgame

import furhatos.event.CustomEvent
import furhatos.event.EventSystem
import furhatos.event.monitors.MonitorGaze
import furhatos.event.monitors.MonitorSpeechWord
import furhatos.flow.kotlin.dialogLogger
import furhatos.util.CommonUtils
import java.io.File
import java.io.PrintWriter

object logger {

    init {
        EventSystem.addEventListener {
            if (logging) {
                if (it !is MonitorGaze && it !is MonitorSpeechWord) {
                    eventLogFile?.println(it.toJSON())
                }
            }
        }
    }

    var logging = false
    var eventLogFile: PrintWriter? = null

    fun startLogging() {
        if (!logging) {
            logging = true
            val timestamp = dialogLogger.timestamp("yyyy-MM-dd HH-mm-ss-SSS")
            val logName = "CardGameRALL $timestamp"
            CommonUtils.getAppDataDir("logs/$groupCode/camera/").mkdirs()
            CommonUtils.getAppDataDir("logs/$groupCode/screen/").mkdirs()
            dialogLogger.startSession(name = logName, directory = "/$groupCode/")
            val audioFile = "logs/$groupCode/$logName/recording.wav"
            EventSystem.send(CustomEvent.Builder("StartRecording").setField("file", audioFile).buildEvent())
            eventLogFile = PrintWriter(CommonUtils.getAppDataDir("logs/$groupCode/$logName/events.txt"))
        }
    }

    fun stopLogging() {
        if (logging) {
            logging = false
            dialogLogger.endSession()
            EventSystem.send(CustomEvent.Builder("StopRecording").buildEvent())
            eventLogFile?.close()
        }
    }
}