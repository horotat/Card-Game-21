package furhatos.app.cardgame.audiosource

import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.io.*
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import kotlin.concurrent.thread

class ScreenCapture {

    private val robot = Robot()
    private var captureThread: Thread? = null
    private var running = false

    fun start(file: File) {
        stop()
        captureThread = thread(start = true) {
            running = true
            val startTime = System.currentTimeMillis()
            val outputStream: OutputStream = FileOutputStream(file)
            while (running) {
                val rectangle = Rectangle(Toolkit.getDefaultToolkit().getScreenSize())
                val bufferedImage = robot.createScreenCapture(rectangle)
                val output = ByteArrayOutputStream()
                ImageIO.write(bufferedImage, "jpg", output)
                val timestamp = (System.currentTimeMillis() - startTime).toInt()
                val header = ByteBuffer.allocate(8)
                header.putInt(timestamp)
                header.putInt(output.size())
                outputStream.write(header.array())
                outputStream.write(output.toByteArray(), 0, output.size())
                outputStream.flush()
                Thread.sleep(100)
            }
            outputStream.close()
        }
    }

    fun stop() {
        if (running) {
            running = false
            captureThread?.join()
        }
    }

}