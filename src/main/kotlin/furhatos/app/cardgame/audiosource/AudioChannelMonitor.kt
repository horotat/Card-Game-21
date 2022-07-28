package furhatos.app.cardgame.audiosource

import furhatos.event.CustomEvent
import furhatos.event.Event
import furhatos.event.EventListener
import furhatos.event.EventSystem
import furhatos.event.actions.ActionListen
import furhatos.event.senses.SenseSpeech
import furhatos.util.CommonUtils
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine
import javax.swing.*
import kotlin.concurrent.thread
import kotlin.system.exitProcess


class AudioChannelMonitor() : JFrame(), AudioLevelMeter.Listener, EventListener {

    private val doScreenCapture = true
    private val screenCapture = ScreenCapture()
    private var brokerAddress = "192.168.1.29"
    private val audioMonitorSettings = File(System.getProperty("user.home"), "audioMonitor.txt")
    private val inputAudioFormat = AudioFormat(16000F, 16, 2, true, false)
    private val inputDevices = AudioUtils.getInputDevices {it.channels == 2}
    private val outputDevices = AudioUtils.getOutputDevices()
    private val audioLevelMeter = AudioLevelMeter(inputAudioFormat, 10)
    private var selectedDevice = 0
    private val connectButton: JButton
    private var connectedToBroker = false
    private var channelLevels = doubleArrayOf(0.0, 0.0)
    private var threshold: Double
    private var activeChannel = -1
    //private var updateCountdown = 0
    private var audioRepeater: ((ByteArray)->Unit)? = null
    private var audioRecorder = AudioRecorder()
    private val audioLevelPanel: AudioLevelPanel
    private val extraThreshold = 5
    private var outputDevice: SourceDataLine? = null
    private var listening = false
    private val recordingButton: JButton
    private val audioMonitor = AudioMonitor(inputAudioFormat) {
        audioLevelMeter.processAudio(it)
        audioRepeater?.invoke(it)
        audioRecorder?.recordAudio(it)
    }

    init {
        title = "Audio Monitor"

        threshold = try {
            audioMonitorSettings.readText().toDoubleOrNull()?:30.0
        } catch (e: IOException) {
            30.0
        }

        audioLevelMeter.listeners += this

        audioMonitor.start(inputDevices[selectedDevice].first)

        add(comboBox(inputDevices.map { it.second }) {
            selectedDevice = selectedIndex
            audioMonitor.start(inputDevices[selectedDevice].first)
        }, BorderLayout.NORTH)

        audioLevelPanel = AudioLevelPanel()
        addCenter(audioLevelPanel)

        val textField = JTextField(brokerAddress)
        connectButton = button("Connect") {
            connectToBroker(textField.text)
        }

        recordingButton = button("Start recording") {
            if (!audioRecorder.recording) {
                startRecording()
            } else {
                stopRecording()
            }
        }

        addSouth(panel {
            addNorth(recordingButton)
            addWest(JLabel("Furhat address"))
            addCenter(textField)
            addEast(connectButton)
            /*
            addSouth(panel {
                addCenter(comboBox(listOf("") + outputDevices.map { it.second }) {
                    setOutputDevice(selectedIndex)
                })
                addWest(JLabel("Monitor on output device"))
            })
            */
        })

        pack()

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                audioRecorder.stop()
                exitProcess(0)
            }
        })
        isVisible = true
    }

    fun startRecording(file: File? = null) {
        recordingButton.text = "Recording (stop)"
        val audioFile = file?:File("c:/recordings/" + DateTimeFormatter.ofPattern(
            "yyyy-MM-dd/HH-mm-ss")
            .withLocale( Locale.UK )
            .withZone( ZoneId.systemDefault()).format(Instant.now()) + ".wav")
        audioFile.parentFile.mkdirs()
        if (connectedToBroker) {
            val videoFile = File(audioFile.absolutePath.replace(".wav", ""))
            VideoRecorder.startRecording(brokerAddress, videoFile)
        }
        if (doScreenCapture) {
            val videoFile = File(audioFile.absolutePath.replace(".wav", ".screen"))
            screenCapture.start(videoFile)
        }
        audioRecorder.start(inputAudioFormat, audioFile)
    }

    fun stopRecording() {
        recordingButton.text = "Start recording"
        audioRecorder.stop()
        screenCapture.stop()
        VideoRecorder.stopRecording()
    }

    fun setOutputDevice(index: Int) {
        if (outputDevice != null) {
            outputDevice!!.stop()
            outputDevice!!.close()
        }
        if (index == 0) {
            audioRepeater = null
        } else {
            outputDevice = AudioSystem.getLine(outputDevices[index + 1].first) as SourceDataLine
            outputDevice!!.open(inputAudioFormat)
            outputDevice!!.start()
            audioRepeater = {
                outputDevice!!.write(it, 0, it.size)
            }
        }
    }

    inner class AudioLevelPanel: JPanel() {

        init {
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    threshold = e.x / wScale
                    audioMonitorSettings.writeText("$threshold")
                    repaint()
                }
            })
        }

        val wScale get() = width / 100.0

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.clearRect(0, 0, width, height)
            val h = height / 2
            for (c in 0..1) {
                if (!listening)
                    g.color = Color.GRAY
                else if (c == activeChannel)
                    g.color = Color.GREEN
                else
                    g.color = Color.YELLOW
                g.fillRect(0, c*h, (channelLevels[c]*wScale).toInt(), h)
            }
            g.color = Color.RED
            val x1 = (threshold * wScale).toInt()
            val x2 = ((threshold + extraThreshold) * wScale).toInt()
            g.drawLine(x1, 0, x1, height)
            g.drawLine(x2, 0, x2, height)
            g.color = Color.BLACK
            g.drawString("LEFT", 30, height / 4)
            g.drawString("RIGHT", 30, height * 3  / 4)
        }

        fun update() {
            repaint()
        }

        override fun getPreferredSize(): Dimension {
            return Dimension(300,100)
        }
    }

    fun connectToBroker(address: String) {
        if (!connectedToBroker) {
            val t = thread {
                try {
                    connectButton.text = "Connecting..."
                    EventSystem.connectToBroker("broker", address, 1932)
                    thread(start = true) { EventSystem.runUntilStopped() }
                    EventSystem.addEventListener(this)
                    connectedToBroker = true
                    brokerAddress = address
                    connectButton.text = "Connected"
                } catch (e: IOException) {
                    println("Could not connect to broker")
                }
            }
            thread {
                Thread.sleep(5000)
                if (!connectedToBroker) {
                    println("Failed to connect to broker")
                    t.stop()
                    connectButton.text = "Connection failed"
                }
            }
        }
    }

    override fun audioLevel(levels: Array<DoubleArray>) {
        this.channelLevels = levels.map { it.average() }.toDoubleArray()
        val (channel, level) = channelLevels.mapIndexed { c, l -> c to l}.sortedByDescending {it.second}[0]
        if (listening && activeChannel == -1 && level >= threshold + extraThreshold) {
            activeChannel = channel
            //println("Active channel: $activeChannel")
            if (connectedToBroker) {
                EventSystem.send(CustomEvent.Builder("SenseSpeechChannel").setField("channel", channel).buildEvent())
            }
        } else if (activeChannel != -1 && level < threshold) {
            activeChannel = -1
            //println("Active channel: $activeChannel")
        }
        audioLevelPanel.update()
    }

    override fun onEvent(event: Event) {
        if (event is ActionListen) {
            listening = true
        } else if (event is SenseSpeech) {
            listening = false
        } else if (event.event_name == "StartRecording") {
            val logDir = CommonUtils.getAppDataDir(event.getString("file"))
            startRecording(logDir)
        } else if (event.event_name == "StopRecording") {
            stopRecording()
        }
    }

}

fun main() {
    AudioChannelMonitor()
}