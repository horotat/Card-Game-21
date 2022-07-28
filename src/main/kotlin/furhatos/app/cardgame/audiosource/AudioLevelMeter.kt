package furhatos.app.cardgame.audiosource


import javax.sound.sampled.AudioFormat

class AudioLevelMeter(audioFormat: AudioFormat, windowSize: Int = 1) {

    private val audioUtils = AudioUtils(audioFormat)
    private val channels = audioFormat.channels
    private val sampleSize = audioFormat.sampleSizeInBits / 8
    private var inputSamples = DoubleArray(0)
    private val frameBuffer = DoubleArray(2048)
    private var frameBufferPos = 0
    private val frame = DoubleArray(((audioFormat.sampleRate*channels) / 100).toInt())
    private val window = Array(channels) {DoubleArray(windowSize) {0.0} }
    val listeners = mutableListOf<Listener>()

    interface Listener {
        fun audioLevel(level: Array<DoubleArray>)
    }

    fun processAudio(samples: ByteArray) {
        if (inputSamples.size != samples.size / sampleSize) {
            inputSamples = DoubleArray(samples.size / sampleSize)
        }
        audioUtils.bytesToDoubles(samples, 0, samples.size, inputSamples, 0)
        System.arraycopy(inputSamples, 0, frameBuffer, frameBufferPos, inputSamples.size)
        frameBufferPos += inputSamples.size
        while (frameBufferPos >= frame.size) {
            System.arraycopy(frameBuffer, 0, frame, 0, frame.size)
            frameBufferPos -= frame.size
            System.arraycopy(frameBuffer, frame.size, frameBuffer, 0, frameBufferPos)

            val levels =
                (0 until channels).map {c ->
                    audioUtils.power(frame, c)
                }
            if (window[0].size > 1)
                window.indices.forEach {c -> (window[c].size-1).downTo(1).forEach { t -> window[c][t] = window[c][t-1] }}
            levels.indices.forEach {c -> window[c][0] = levels[c]}
            listeners.forEach {
                it.audioLevel(window)
            }
        }
    }


}

