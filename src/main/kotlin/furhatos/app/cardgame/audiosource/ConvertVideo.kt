package furhatos.app.cardgame.audiosource

import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import javax.imageio.ImageIO

fun convertToVideo(inputFile: File) {
    println("Reading from $inputFile")
    val outputFile = File(inputFile.absolutePath.replace("\\.[^\\.]+$".toRegex(), ".mp4"))
    println("Writing to $outputFile")
    val videoEncoder = VideoEncoder()
    videoEncoder.startEncoding(outputFile)
    val inputStream = FileInputStream(inputFile)
    val headerArray = ByteArray(8)
    val header = ByteBuffer.wrap(headerArray)

    while (true) {
        try {
            val read = inputStream.read(headerArray)
            if (read != 8) {
                break
            }
            val timestamp = header.getInt(0) / 1000.0
            val size = header.getInt(4)
            val imageData = ByteArray(size)
            inputStream.read(imageData)
            val img = ImageIO.read(ImageIO.createImageInputStream(ByteArrayInputStream(imageData)))
            if (img != null) {
                videoEncoder.encodeImage(img, timestamp)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            break
        }
    }

    inputStream.read()
    inputStream.close()
    videoEncoder.stopEncoding()
    println("Done")
}

fun main(args: Array<String>) {

    val dataPath = File("/Users/ali/Library/CloudStorage/OneDrive-KTH/Projects/CardGame 2021/VRS/Data/CG21G18/CardGameRALL 2022-01-11 13-23-05-870/recording.video")
    convertToVideo(dataPath)

//    dataPath.walk().forEach {
//        if (it.length() > 0 && it.isFile && it.name.endsWith(".video")) {
//            println("Converting $it")
//            convertToVideo(it)
//        }
//    }
}