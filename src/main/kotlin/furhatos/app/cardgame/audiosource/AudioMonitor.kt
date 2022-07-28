package furhatos.app.cardgame.audiosource

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.sound.sampled.*

class AudioMonitor(val audioFormat: AudioFormat, val listener: (ByteArray)->Unit) {

    var audioMonitorThread: AudioMonitorThread? = null

    fun start(targetDataLineInfo: Line.Info) {
        stop()
        val line = AudioSystem.getLine(targetDataLineInfo) as TargetDataLine
        audioMonitorThread = AudioMonitorThread(line)
        audioMonitorThread!!.start()
    }

    fun stop() {
        if (audioMonitorThread != null) {
            audioMonitorThread!!.playing = false
            audioMonitorThread!!.join()
            audioMonitorThread = null
        }
    }

    inner class AudioMonitorThread(val line: TargetDataLine): Thread() {
        var playing = true
        override fun run() {
            line.open(audioFormat)
            line.start()
            val b = ByteArray(320)
            while (playing) {
                line.read(b, 0, b.size)
                listener(b)
            }
            line.stop()
            line.close()
        }
    }

}