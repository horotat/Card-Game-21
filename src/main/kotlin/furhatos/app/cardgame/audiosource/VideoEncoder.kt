package furhatos.app.cardgame.audiosource

import com.xuggle.xuggler.*
import com.xuggle.xuggler.video.ConverterFactory
import com.xuggle.xuggler.video.IConverter
import furhatos.util.CommonUtils
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException

private val logger = CommonUtils.getLogger(VideoEncoder::class.java)

class VideoEncoder {

    private var coder: IStreamCoder? = null
    private var container: IContainer? = null
    var encoding = false
        private set
    private var headerWritten = false
    private var encframe = 0
    private val bitRate = 500000
    private var firstTimeStamp: Double = 0.0

    @Synchronized
    fun startEncoding(file: File) {
        container = IContainer.make()
        if (container!!.open(
                file.getAbsolutePath(),
                IContainer.Type.WRITE,
                null
            ) < 0
        ) throw IOException("Failed to start recording")
        encoding = true
        headerWritten = false
        val videoCodec = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_H264)
        val stream = container!!.addNewStream(videoCodec)
        coder = stream.streamCoder
    }

    @Synchronized
    fun stopEncoding() {
        encoding = false
        if (container!!.writeTrailer() < 0) throw IOException("Failed to stop recording")
        container!!.close()
    }

    @Synchronized
    fun encodeImage(image: BufferedImage, imageTimestamp: Double) {
        if (!headerWritten) {
            //ICodec codec = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_FLV1);
            //ICodec codec = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_H264);
            //coder = IStreamCoder.make(Direction.ENCODING, codec);
            //coder!!.setCodec(ICodec.ID.CODEC_ID_H264)
            coder!!.numPicturesInGroupOfPictures = 15
            coder!!.bitRate = bitRate
            coder!!.pixelType = IPixelFormat.Type.YUV420P
            coder!!.height = image.height
            coder!!.width = image.width
            //coder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
            //coder.setGlobalQuality(0);
            val frameRate: IRational = IRational.make(25, 1)
            coder!!.frameRate = frameRate
            coder!!.timeBase = IRational.make(frameRate.denominator, frameRate.numerator)
            firstTimeStamp = imageTimestamp
            coder!!.open(null, null)
            encframe = 0

            if (container!!.writeHeader() < 0)
                logger.error("Could not write header");
            headerWritten = true;
        }
        val packet = IPacket.make()
        val converter: IConverter = ConverterFactory.createConverter(image, coder!!.pixelType)
        val timeStamp: Long = ((imageTimestamp - firstTimeStamp) * 1000000).toLong()

        //System.out.println("Encoding image " + timeStamp);
        val outFrame: IVideoPicture = converter.toPicture(image, timeStamp)
        if (encframe % 25 == 0) {
            //make regular keyframes
            outFrame.isKeyFrame = true
        }
        encframe++
        outFrame.quality = 0;
        if (coder!!.encodeVideo(packet, outFrame, 0) < 0) {
            println("Error encoding")
        }
        // System.out.println(coder.encodeVideo(null, outFrame, -1));

        outFrame.delete()
        converter.delete()
        packet.streamIndex = 0
        if (packet.isComplete) {
            if (container!!.writePacket(packet) < 0)
                throw IOException("could not write packet")
        }
    }

}