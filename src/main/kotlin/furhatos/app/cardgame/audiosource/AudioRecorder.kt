package furhatos.app.cardgame.audiosource

import java.io.*
import javax.sound.sampled.*

class AudioRecorder() {

    private var fileOutputStream: OutputStream? = null
    private var file: File? = null
    private var bytesWritten: Int = 0
    val recording get() = fileOutputStream != null

    @Synchronized
    fun start(audioFormat: AudioFormat, file: File) {
        stop()
        bytesWritten = 0
        this.file = file
        AudioSystem.write(AudioInputStream(ByteArrayInputStream(ByteArray(0)), audioFormat, -1), AudioFileFormat.Type.WAVE, file)
        fileOutputStream = FileOutputStream(file, true)
    }

    @Synchronized
    fun stop() {
        if (recording) {
            fileOutputStream!!.close()
            val fmtChunkSize = 16
            val headerSize = 28 + fmtChunkSize
            val dataLength: Int = bytesWritten //- headerSize
            val riffLength: Int = dataLength + headerSize - 8
            val raf = RandomAccessFile(this.file!!, "rw")
            raf.skipBytes(4)
            raf.writeInt(AudioUtils.big2little(riffLength))
            raf.skipBytes(4 + 4 + 4 + fmtChunkSize + 4)
            raf.writeInt(AudioUtils.big2little(dataLength))
            raf.close()
            fileOutputStream = null
        }
    }

    @Synchronized
    fun recordAudio(b: ByteArray) {
        if (recording) {
            fileOutputStream!!.write(b)
            bytesWritten += b.size
        }
    }

}