package furhatos.app.cardgame.audiosource

import org.zeromq.SocketType
import org.zeromq.ZMQ
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import kotlin.concurrent.thread

object VideoRecorder {

    var recording = false
    var recordingThread: Thread? = null

    @Synchronized
    fun startRecording(address: String, file: File) {
        if (recording) {
            stopRecording()
        }
        val startTime = System.currentTimeMillis()
        val snapshotFile = File(file.absolutePath + ".jpg")
        val videoFile = File(file.absolutePath + ".video")
        val outputStream = FileOutputStream(videoFile)
        var takeSnapshot = true
        val context: ZMQ.Context = ZMQ.context(1)
        val socket = context.socket(SocketType.SUB)
        socket.receiveTimeOut = 500
        socket.subscribe(byteArrayOf())
        socket.connect("tcp://$address:3000")
        recording = true
        recordingThread = thread {
            while (recording) {
                val data = socket.recv()
                if (data != null && data.isNotEmpty()) {
                    val timestamp = (System.currentTimeMillis() - startTime).toInt()
                    val header = ByteBuffer.allocate(8)
                    header.putInt(timestamp)
                    header.putInt(data.size)
                    outputStream.write(header.array())
                    outputStream.write(data)
                    if (takeSnapshot) {
                        val img = ImageIO.read(ImageIO.createImageInputStream(ByteArrayInputStream(data)))
                        if (img != null) {
                            takeSnapshot = false
                            ImageIO.write(img, "jpg", snapshotFile)
                        }
                    }
                }
            }
            socket.close()
            outputStream.close()
        }
    }

    @Synchronized
    fun stopRecording() {
        if (recording) {
            recording = false
            recordingThread!!.join()
        }
    }
}