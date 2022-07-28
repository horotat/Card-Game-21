package furhatos.app.cardgame.audiosource

import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.sound.sampled.*

class AudioUtils(val audioFormat: AudioFormat) {

    fun wrap(source: ByteArray): ByteBuffer {
        val bb = ByteBuffer.wrap(source)
        if (audioFormat.isBigEndian) {
            bb.order(ByteOrder.BIG_ENDIAN)
        } else {
            bb.order(ByteOrder.LITTLE_ENDIAN)
        }
        return bb
    }

    /**
     * Converts bytes to doubles (from -1.0 to 1.0) according to format
     */
    fun bytesToDoubles(
        source: ByteArray, sourcePos: Int,
        sourceLen: Int, target: DoubleArray, targetPos: Int
    ) {
        val bb = wrap(source)
        for (i in 0 until sourceLen / 2) {
            val short = bb.getShort(i * 2 + sourcePos)
            target[i + targetPos] = short.toDouble() / Short.MAX_VALUE.toDouble()
        }
    }

    /**
     * Calculates the power of the samples
     */
    fun power(samples: DoubleArray, channel: Int): Double {
        var sumOfSquares = 0.0
        for (i in samples.indices step audioFormat.channels) {
            val sample = samples[i + channel]
            sumOfSquares += Math.pow(sample, 2.0)
        }
        val power = 10.0 * Math.log10(Math.sqrt(sumOfSquares / (samples.size / audioFormat.channels))) + 50
        return power.coerceIn(0.0..99.0)
    }

    fun toSingleChannel(inputByteArray: ByteArray): ByteArray {
        val inputBuffer = wrap(inputByteArray)
        val outputByteArray = ByteArray(inputByteArray.size / 2)
        val outputBuffer = wrap(outputByteArray)
        for (i in 0 until inputByteArray.size / 4) {
            val short = ((inputBuffer.getShort(i * 2).toInt() + inputBuffer.getShort(i * 2 + 1).toInt()) / 2).toShort()
            //println("" + short + " " + (inputBuffer.getShort(i * 2)) + " " + (inputBuffer.getShort(i * 2 + 1)))
            outputBuffer.putShort(i, inputBuffer.getShort(i * 2))
        }
        return outputByteArray
    }

    companion object {
        fun getInputDevices(filter: (AudioFormat)->Boolean = {true}): List<Pair<Line.Info, String>> {
            val devices = mutableListOf<Pair<Line.Info, String>>()
            for (mi in AudioSystem.getMixerInfo()) {
                for (ti in AudioSystem.getMixer(mi).targetLineInfo) {
                    if (ti.lineClass == TargetDataLine::class.java) {
                        if ((ti as DataLine.Info).formats.any { filter(it) }) {
                            devices.add(ti to mi.name)
                        }
                    }
                }
            }
            return devices
        }
        fun getOutputDevices(filter: (AudioFormat)->Boolean = {true}): List<Pair<Line.Info, String>> {
            val devices = mutableListOf<Pair<Line.Info, String>>()
            for (mi in AudioSystem.getMixerInfo()) {
                for (ti in AudioSystem.getMixer(mi).sourceLineInfo) {
                    if (ti.lineClass == SourceDataLine::class.java) {
                        if ((ti as DataLine.Info).formats.any { filter(it) }) {
                            devices.add(ti to mi.name)
                        }
                    }
                }
            }
            return devices
        }
        fun big2little(i: Int): Int {
            var i = i
            val b1 = i and 0xFF shl 24
            val b2 = i and 0xFF00 shl 8
            val b3 = i and 0xFF0000 shr 8
            val b4 = i and -0x1000000 ushr 24
            i = b1 or b2 or b3 or b4
            return i
        }

    }

}

