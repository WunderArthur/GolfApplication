package com.example.myapplication5.presentation

import android.os.Bundle
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Environment
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.*
import com.example.golfSwingApplication.presentation.screens.GolfClubSelectionScreen
import com.example.golfSwingApplication.presentation.screens.MainMenuScreen
import com.example.golfSwingApplication.presentation.screens.accelerationTrainer.TargetAccelerationDisplayScreen
import com.example.golfSwingApplication.presentation.screens.accelerationTrainer.AccelerationTrainingSessionResultScreen
import com.example.golfSwingApplication.presentation.screens.accelerationTrainer.AccelerationTrainingSessionScreen
import com.example.golfSwingApplication.presentation.screens.accelerationTrainer.TargetAccelerationScreen
import com.example.golfSwingApplication.presentation.screens.accelerationTrainer.PastAccelerationTrainingSessionScreen
import com.example.golfSwingApplication.presentation.screens.accelerationTrainer.TargetAccelerationResultScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.AskForTipsScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.BallFlightDirectionScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.PastDirectionTrainingSessionScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.DirectionSensorRecordingSummaryScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.DirectionSensorSelectionScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.FusionSensorDataScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.TipScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.DirectionTrainingSessionResultScreen
import com.example.golfSwingApplication.presentation.screens.directionTrainer.DirectionTrainingSessionScreen
import com.example.golfSwingApplication.presentation.screens.singleSensor.AccelerationDisplayScreen
import com.example.golfSwingApplication.presentation.screens.singleSensor.GyroscopeDataScreen
import com.example.golfSwingApplication.presentation.screens.singleSensor.SingleSensorSelectionScreen
import com.example.golfSwingApplication.presentation.theme.GolfApplicationTheme

import kotlin.math.sqrt
import java.io.BufferedReader
import java.io.FileReader

/**
 *
 * MainActivity class for handling sensor events and managing the UI states.
 */

class MainActivity : ComponentActivity(), SensorEventListener {

    // Sensor manager for handling sensor events
    private lateinit var sensorManager: SensorManager

    // Mutable states for storing various data
    private val acceleration = mutableStateOf("")
    private val gyroscopeData = mutableStateOf("")
    private val drawTips = listOf("Align your body correctly.", "Loosen your grip.", "Open the clubface in the backswing and keep your wrist and forearm straight.","Add a bit of forearm rotation through impact.")
    private val sliceTips = listOf("Align your body correctly.", "Strengthen your grip.", "Point the clubface down in the first quarter of the backswing.","Rotate the clubface through impact.","Swing more from the inside through impact.")
    private val trainingSessionResults = mutableStateListOf<Triple<String, String, String>>()
    private val positionCalculator = PositionCalculator()
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var accelerometerData = mutableStateListOf<FloatArray>()
    private var gyroscopeDataList = mutableStateListOf<FloatArray>()
    private var isGyroscopeRecording = mutableStateOf(false)
    private var isAccelerometerRecording = mutableStateOf(false)
    private var startTime: Long = 0
    private var fileWriter: FileWriter? = null

    // UI state variables
    private var showMainMenu = mutableStateOf(true)
    private var showHandPositionScreen = mutableStateOf(false)
    private var showGolfClubSelectionScreen = mutableStateOf(false)
    private var showAccelerationGolfClubSelectionScreen = mutableStateOf(false)
    private var showTargetAccelerationScreen = mutableStateOf(false)
    private var showTrainingSessionScreen = mutableStateOf(false)
    private var showTrainingSessionResultScreen = mutableStateOf(false)
    private var selectedClub = mutableStateOf("")
    private var targetAcceleration = mutableStateOf(0f)
    private var maxAcceleration = mutableStateOf(0f)
    private var isGraphTraining = mutableStateOf(false)
    private var fusedData = mutableListOf<FloatArray>()
    private var isFusionRecording = mutableStateOf(false)
    private var fusionFileWriter: FileWriter? = null
    private var handPositionData = mutableListOf<FloatArray>()
    private var showBallFlightDirectionScreen = mutableStateOf(false) // New state
    private var flightDirectionTag = "" // New variable for direction tag
    private var isTrainingSession = mutableStateOf(false)
    private var showSensorDataScreen = mutableStateOf(false)
    private var showRecordingSummaryScreen = mutableStateOf(false)
    private var recordingDuration = mutableStateOf(0f)
    private var sensorData = mutableListOf<FloatArray>()
    private var currentSensorData = mutableStateOf(floatArrayOf(0f, 0f, 0f, 0f)) // New state variable
    private var showSingleSensorSelectionScreen = mutableStateOf(false)
    private var selectedSensorType = mutableStateOf("")
    private var showPastTrainingSessionsScreen = mutableStateOf(false)
    private var showGyroscopeDataScreen = mutableStateOf(false)
    private var tempFusionFile: File? = null
    private var showTargetAccelerationResultScreen = mutableStateOf(false)
    private var showTargetAccelerationDisplayScreen = mutableStateOf(false)
    private var showAccelerationDisplayScreen = mutableStateOf(false)
    private var showAccelerationTrainingSessionScreen = mutableStateOf(false)
    private var showPastAccelerationTrainingSessionsScreen = mutableStateOf(false)
    private var showPastAccelerationTrainingSessionResultScreen = mutableStateOf(false)
    private var showAccelerationTrainingSessionResultScreen = mutableStateOf(false)
    private var leftHitCount = mutableStateOf(0)
    private var rightHitCount = mutableStateOf(0)
    private var showAskForTipsScreen = mutableStateOf(false)
    private var showTipScreen = mutableStateOf(false)
    private var currentTip = mutableStateOf("")
    private var currentTipIndex = mutableStateOf(0)
    private var isDrawTips = mutableStateOf(true)
    private var tempGyroscopeFile: File? = null


    /**
     * Called when the activity is first created.
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep the screen on while the activity is running
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Initialize the sensor manager and get default sensors
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        // Register sensor listeners for accelerometer and gyroscope
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)

        // Set the content view with the theme
        setContent {
            GolfApplicationTheme {
                // Conditional rendering based on the current state
                when {

                    // DIRECTION TRAINER

                    // Display the MainMenuScreen if showMainMenu is true
                    showMainMenu.value -> MainMenuScreen(
                        onHandPosition = {
                            isGraphTraining.value = false
                            showMainMenu.value = false
                            showTrainingSessionScreen.value = true
                        },
                        onAccelerationTrainer = {
                            showMainMenu.value = false
                            showAccelerationTrainingSessionScreen.value = true
                        },
                        onSingleSensor = {
                            showMainMenu.value = false
                            showSingleSensorSelectionScreen.value = true
                        }
                    )

                    // Display the DirectionTrainingSessionScreen if showTrainingSessionScreen is true
                    showTrainingSessionScreen.value -> DirectionTrainingSessionScreen(
                        onStartTrainingSession = {
                            isTrainingSession.value = true
                            showTrainingSessionScreen.value = false
                            showGolfClubSelectionScreen.value = true
                        },
                        onPastTrainingSessions = {
                            showTrainingSessionScreen.value = false
                            showPastTrainingSessionsScreen.value = true
                        },
                        onBack = {
                            showTrainingSessionScreen.value = false
                            showMainMenu.value = true
                        }
                    )

                    // Display the PastDirectionTrainingSessionScreen if showPastTrainingSessionsScreen is true
                    showPastTrainingSessionsScreen.value -> PastDirectionTrainingSessionScreen(
                        sessionFiles = getDirectionTrainingSessionFiles(),
                        onSessionSelected = { fileName ->
                            trainingSessionResults.clear()
                            trainingSessionResults.addAll(loadDirectionTrainingSessionResults(fileName))
                            showPastTrainingSessionsScreen.value = false
                            showTrainingSessionResultScreen.value = true
                        },
                        onBack = {
                            showPastTrainingSessionsScreen.value = false
                            showTrainingSessionScreen.value = true
                        }
                    )

                    // Display the GolfClubSelectionScreen if showGolfClubSelectionScreen is true
                    showGolfClubSelectionScreen.value -> GolfClubSelectionScreen(
                        onClubSelected = { club ->
                            selectedClub.value = club
                            showGolfClubSelectionScreen.value = false
                            showHandPositionScreen.value = true
                        },
                        onBack = {
                            showGolfClubSelectionScreen.value = false
                            showMainMenu.value = true
                        }
                    )

                    // Display the DirectionSensorSelectionScreen if showHandPositionScreen is true
                    showHandPositionScreen.value -> DirectionSensorSelectionScreen(
                        handPositionData = handPositionData,
                        onBack = {
                            showHandPositionScreen.value = false
                            showGolfClubSelectionScreen.value = true
                        },
                        onStartRecording = {
                            startFusionRecording()
                            showSensorDataScreen.value = true
                            showHandPositionScreen.value = false
                        },
                        onStopRecording = {
                            stopFusionRecording()
                            showBallFlightDirectionScreen.value = true
                            showSensorDataScreen.value = false
                        },
                        onStopRecordingAndNavigate = {
                            stopFusionRecording()
                            showBallFlightDirectionScreen.value = true
                            showHandPositionScreen.value = false
                        },
                        isRecording = isFusionRecording.value,
                        onEndTrainingSession = {
                            showTrainingSessionResultScreen.value = true
                            showHandPositionScreen.value = false
                            saveDirectionTrainingSessionResults()
                        }
                    )

                    // Display the BallFlightDirectionScreen if showBallFlightDirectionScreen is true
                    showBallFlightDirectionScreen.value -> BallFlightDirectionScreen(
                        onDirectionSelected = { direction ->
                            flightDirectionTag = direction

                            // Update hit counts and check for consecutive hits
                            if (direction == "left") {
                                leftHitCount.value += 1
                                rightHitCount.value = 0
                            } else if (direction == "right") {
                                rightHitCount.value += 1
                                leftHitCount.value = 0
                            } else {
                                leftHitCount.value = 0
                                rightHitCount.value = 0
                            }

                            // Add the current hit to the session results
                            if (isTrainingSession.value) {
                                trainingSessionResults.add(Triple(selectedClub.value, direction, "Recorded"))
                            }

                            // Check for tips display
                            if (leftHitCount.value >= 3) {
                                currentTipIndex.value = 0
                                currentTip.value = drawTips[currentTipIndex.value]
                                isDrawTips.value = true
                                showBallFlightDirectionScreen.value = false
                                showAskForTipsScreen.value = true
                            } else if (rightHitCount.value >= 3) {
                                currentTipIndex.value = 0
                                currentTip.value = sliceTips[currentTipIndex.value]
                                isDrawTips.value = false
                                showBallFlightDirectionScreen.value = false
                                showAskForTipsScreen.value = true
                            } else {
                                initFusionFileWriter()
                                showBallFlightDirectionScreen.value = false
                                showRecordingSummaryScreen.value = true
                            }
                        }
                    )

                    // Display the FusionSensorDataScreen if showSensorDataScreen is true
                    showSensorDataScreen.value -> FusionSensorDataScreen(
                        currentSensorData = currentSensorData.value,
                        onStopRecording = {
                            stopFusionRecording()
                            showBallFlightDirectionScreen.value = true
                            showSensorDataScreen.value = false
                        }
                    )

                    // Display the DirectionTrainingSessionResultScreen if showTrainingSessionResultScreen is true
                    showTrainingSessionResultScreen.value -> DirectionTrainingSessionResultScreen(
                        results = trainingSessionResults,
                        onBack = {
                            trainingSessionResults.clear()
                            isTrainingSession.value = false
                            showTrainingSessionResultScreen.value = false
                            showMainMenu.value = true
                        }
                    )


                    // Display the AskForTipsScreen if showAskForTipsScreen is true
                    showAskForTipsScreen.value -> AskForTipsScreen(
                        message = if (isDrawTips.value) "Would you like tips on how to avoid a draw?" else "Would you like tips on how to avoid a slice?",
                        onYes = {
                            showAskForTipsScreen.value = false
                            showTipScreen.value = true
                        },
                        onNo = {
                            showAskForTipsScreen.value = false
                            showGolfClubSelectionScreen.value = true
                        }
                    )

                    // Display the showAskForTipsScreen if showTipScreen is true
                    showTipScreen.value -> TipScreen(
                        tipText = currentTip.value,
                        onAnotherTip = {
                            if (currentTipIndex.value < 3) {
                                currentTipIndex.value += 1
                                currentTip.value = if (isDrawTips.value) drawTips[currentTipIndex.value] else sliceTips[currentTipIndex.value]
                            } else {
                                // No more tips available
                            }
                        },
                        onContinueTraining = {
                            showTipScreen.value = false
                            showGolfClubSelectionScreen.value = true
                        }
                    )

                    // Display the DirectionSensorRecordingSummaryScreen if showRecordingSummaryScreen is true
                    showRecordingSummaryScreen.value -> DirectionSensorRecordingSummaryScreen(
                        maxAcceleration = maxAcceleration.value,
                        recordingDuration = recordingDuration.value,
                        onBack = {
                            showHandPositionScreen.value = true
                            showRecordingSummaryScreen.value = false
                        },
                        onGolfSelectionScreen = {
                            showGolfClubSelectionScreen.value = true
                            showRecordingSummaryScreen.value = false
                        },
                        onEndTrainingSession = {
                            showTrainingSessionResultScreen.value = true
                            showHandPositionScreen.value = false
                            saveDirectionTrainingSessionResults()
                        }
                    )



                    //SINGLE SENSOR RECORDING


                    // Display the SingleSensorSelectionScreen if showSingleSensorSelectionScreen is true
                    showSingleSensorSelectionScreen.value -> SingleSensorSelectionScreen(
                        onSelectSensor = { sensorType ->
                            selectedSensorType.value = sensorType
                            showSingleSensorSelectionScreen.value = false
                            if (sensorType == "Gyroscope") {
                                showGyroscopeDataScreen.value = true
                            } else {
                                showAccelerationDisplayScreen.value = true
                            }
                        },
                        onBack = {
                            showSingleSensorSelectionScreen.value = false
                            showMainMenu.value = true
                        }
                    )

                    // Display the GyroscopeDataScreen if showGyroscopeDataScreen is true
                    showGyroscopeDataScreen.value -> GyroscopeDataScreen(
                        onBack = {
                            showGyroscopeDataScreen.value = false
                            showSingleSensorSelectionScreen.value = true
                        },
                        onStartRecording = { startGyroscopeRecording() },
                        onStopRecording = { stopGyroscopeRecording() },
                        isRecording = isGyroscopeRecording.value,
                        currentSensorData = currentSensorData.value
                    )

                    // Display the AccelerationDisplayScreen if showAccelerationDisplayScreen is true
                    showAccelerationDisplayScreen.value -> AccelerationDisplayScreen(
                        onBack = {
                            showAccelerationDisplayScreen.value = false
                            showSingleSensorSelectionScreen.value = true
                        },
                        onStartRecording = { startAccelerationRecording() },
                        onStopRecording = {
                            stopAccelerationRecording()
                        },
                        isRecording = isAccelerometerRecording.value,
                        currentSensorData = currentSensorData.value
                    )


                    // ACCELERATION TRAINER


                    // Display the TargetAccelerationDisplayScreen if showTargetAccelerationDisplayScreen is true
                    showTargetAccelerationDisplayScreen.value -> TargetAccelerationDisplayScreen(
                        onBack = {
                            showTargetAccelerationDisplayScreen.value = false
                            showTargetAccelerationScreen.value = true
                        },
                        onStartRecording = { startAccelerationRecording() },
                        onStopRecording = {
                            stopTargetAccelerationSensorRecording()
                            showTargetAccelerationDisplayScreen.value = false
                            showTargetAccelerationResultScreen.value = true
                        },
                        isRecording = isAccelerometerRecording.value,
                        currentSensorData = currentSensorData.value
                    )

                    // Display the TargetAccelerationResultScreen if showTargetAccelerationResultScreen is true
                    showTargetAccelerationResultScreen.value -> TargetAccelerationResultScreen(
                        targetAcceleration = targetAcceleration.value,
                        maxAcceleration = getLastRecordedMaxAcceleration(), // Ensure correct maxAcceleration is displayed
                        onRehit = {
                            showTargetAccelerationResultScreen.value = false
                            showTargetAccelerationScreen.value = true
                        },
                        onNewClub = {
                            showTargetAccelerationResultScreen.value = false
                            showAccelerationGolfClubSelectionScreen.value = true
                        },
                        onEndTrainingSession = {
                            saveAccelerationTrainingSessionResults()
                            showTargetAccelerationResultScreen.value = false
                            showAccelerationTrainingSessionResultScreen.value = true
                        }
                    )

                    // Display the AccelerationTrainingSessionResultScreen if showAccelerationTrainingSessionResultScreen is true
                    showAccelerationTrainingSessionResultScreen.value -> AccelerationTrainingSessionResultScreen(
                        results = trainingSessionResults,
                        onBack = {
                            showAccelerationTrainingSessionResultScreen.value = false
                            showAccelerationTrainingSessionScreen.value = true
                        }
                    )

                    // Display the TargetAccelerationScreen if showTargetAccelerationScreen is true
                    showTargetAccelerationScreen.value -> TargetAccelerationScreen(
                        golfClub = selectedClub.value,
                        onAccelerationSelected = { acceleration ->
                            targetAcceleration.value = acceleration
                            showTargetAccelerationScreen.value = false
                            showTargetAccelerationDisplayScreen.value = true
                        },
                        onBack = {
                            showTargetAccelerationScreen.value = false
                            showMainMenu.value = true
                        },
                        onNext = { acceleration ->
                            targetAcceleration.value = acceleration
                            showTargetAccelerationScreen.value = false
                            showTargetAccelerationDisplayScreen.value = true
                        }
                    )

                    // Display the GolfClubSelectionScreen if showAccelerationGolfClubSelectionScreen is true
                    showAccelerationGolfClubSelectionScreen.value -> GolfClubSelectionScreen(
                        onClubSelected = { club ->
                            selectedClub.value = club
                            showAccelerationGolfClubSelectionScreen.value = false
                            showTargetAccelerationScreen.value = true
                        },
                        onBack = {
                            showAccelerationGolfClubSelectionScreen.value = false
                            showMainMenu.value = true
                        }
                    )

                    // Display the AccelerationTrainingSessionScreen if showAccelerationTrainingSessionScreen is true
                    showAccelerationTrainingSessionScreen.value -> AccelerationTrainingSessionScreen(
                        onStartAccelerationTraining = {
                            isTrainingSession.value = true
                            trainingSessionResults.clear() // Clear previous session results before starting a new session
                            showAccelerationTrainingSessionScreen.value = false
                            showAccelerationGolfClubSelectionScreen.value = true
                        },
                        onPastAccelerationSessions = {
                            showAccelerationTrainingSessionScreen.value = false
                            showPastAccelerationTrainingSessionsScreen.value = true
                        },
                        onBack = {
                            showAccelerationTrainingSessionScreen.value = false
                            showMainMenu.value = true
                        }
                    )

                    // Display the PastAccelerationTrainingSessionScreen if showPastAccelerationTrainingSessionsScreen is true
                    showPastAccelerationTrainingSessionsScreen.value -> PastAccelerationTrainingSessionScreen(
                        sessionFiles = getAccelerationTrainingSessionFiles(),
                        onSessionSelected = { fileName ->
                            trainingSessionResults.clear()
                            trainingSessionResults.addAll(loadAccelerationSessionResults(fileName))
                            showPastAccelerationTrainingSessionsScreen.value = false
                            showPastAccelerationTrainingSessionResultScreen.value = true // Show the results screen
                        },
                        onBack = {
                            showPastAccelerationTrainingSessionsScreen.value = false
                            showAccelerationTrainingSessionScreen.value = true
                        }
                    )

                    // Display the AccelerationTrainingSessionResultScreen if showPastAccelerationTrainingSessionResultScreen is true
                    showPastAccelerationTrainingSessionResultScreen.value -> AccelerationTrainingSessionResultScreen(
                        results = trainingSessionResults,
                        onBack = {
                            showPastAccelerationTrainingSessionResultScreen.value = false
                            showAccelerationTrainingSessionScreen.value = true
                        }
                    )
                }
            }
        }
    }


    /**
     * Called when there is a new sensor event. This method handles the sensor data processing for both accelerometer and gyroscope sensors.
     *
     * @param event The SensorEvent containing the sensor data.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Get the current time and calculate the elapsed time since the start
            val currentTime = System.currentTimeMillis()
            val timeElapsed = (currentTime - startTime) / 1000.0f

            // Create an array of the elapsed time and sensor values
            val data = floatArrayOf(timeElapsed, it.values[0], it.values[1], it.values[2])

            // Update the current sensor data state
            currentSensorData.value = data

            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                // Process accelerometer data
                accelerometerData.add(data)
                if (isAccelerometerRecording.value) {
                    // Write data to file if recording
                    fileWriter?.append("${data.joinToString(",")}\n")
                }

                // Calculate the absolute acceleration
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]
                val absAcc = sqrt(x * x + y * y + z * z)
                acceleration.value = "X:$x Y:$y Z:$z Abs:$absAcc"

                // Update the maximum acceleration if the current value is higher
                if (absAcc > maxAcceleration.value) {
                    maxAcceleration.value = absAcc
                }
            } else if (it.sensor.type == Sensor.TYPE_GYROSCOPE) {
                // Process gyroscope data
                gyroscopeDataList.add(data)
                if (isGyroscopeRecording.value) {
                    // Write data to file if recording
                    fileWriter?.append("${data.joinToString(",")}\n")
                }

                // Update gyroscope data state
                val roll = it.values[0]
                val pitch = it.values[1]
                val yaw = it.values[2]
                gyroscopeData.value = "Roll: $roll\nPitch: $pitch\nYaw: $yaw"
            }

            // If both accelerometer and gyroscope data are available, fuse the data and calculate the hand position
            if (accelerometerData.isNotEmpty() && gyroscopeDataList.isNotEmpty()) {
                val fused = positionCalculator.fuseSensorData(accelerometerData, gyroscopeDataList)
                if (isFusionRecording.value) {
                    // Write fused data to file if recording
                    fusionFileWriter?.append("${fused.last().joinToString(",")}\n")
                }
                fusedData.add(fused.last())
                handPositionData = positionCalculator.calculatePosition(accelerometerData, gyroscopeDataList).toMutableList()
            }
        }
    }



    // GENERAL FUNCTIONS



    /**
     * Called when the accuracy of a sensor has changed.
     * This method is currently not used but is required to implement SensorEventListener.
     *
     * @param sensor The sensor whose accuracy has changed.
     * @param accuracy The new accuracy of the sensor. This is one of SensorManager.SENSOR_STATUS_* constants.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    /**
     * Called when the activity is being destroyed.
     * Unregisters the sensor listener to stop receiving sensor updates.
     */
    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    /**
     * Closes the file writer if it is not null.
     * This method is used to safely close the file writer after writing sensor data to a file.
     */
    private fun closeFileWriter() {
        fileWriter?.close()
    }


    // DIRECTION TRAINER


    /**
     * Loads the results of a direction training session from a CSV file.
     *
     * @param fileName The name of the file containing the training session results.
     * @return A list of triples containing the club, direction, and status of each entry in the session.
     */
    private fun loadDirectionTrainingSessionResults(fileName: String): List<Triple<String, String, String>> {
        val results = mutableListOf<Triple<String, String, String>>()
        val folderName = "Documents/TrainingSessions"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        val file = File(fileDirectory, "$fileName.csv")
        if (file.exists()) {
            BufferedReader(FileReader(file)).use { reader ->
                reader.readLine() // Skip header
                reader.forEachLine { line ->
                    val parts = line.split(",")
                    if (parts.size == 3) {
                        results.add(Triple(parts[0], parts[1], parts[2]))
                    }
                }
            }
        }
        return results
    }

    /**
     * Initializes the file writer for fusion data.
     * Creates a new temporary file to store the fusion data.
     */
    private fun initFusionFileWriter() {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        fileDirectory.mkdirs()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val tempFileName = "temp_fusion_data_${dateFormat.format(Date(startTime))}.csv"
        tempFusionFile = File(fileDirectory, tempFileName)

        fusionFileWriter = FileWriter(tempFusionFile, true)
        fusionFileWriter?.append("\"Time (s)\",\"Fused x\",\"Fused y\",\"Fused z\"\n")
    }

    /**
     * Closes the fusion file writer if it is not null.
     */
    private fun closeFusionFileWriter() {
        fusionFileWriter?.close()
    }

    /**
     * Retrieves the list of direction training session files.
     *
     * @return A list of file names (without extensions) of the direction training sessions.
     */
    private fun getDirectionTrainingSessionFiles(): List<String> {
        val folderName = "Documents/TrainingSessions"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        return fileDirectory.listFiles()?.map { it.nameWithoutExtension }?.toList() ?: emptyList()
    }

    /**
     * Starts recording fusion data.
     * Clears existing data, initializes start time, and resets recording state.
     */
    private fun startFusionRecording() {
        fusedData.clear()
        startTime = System.currentTimeMillis()
        isFusionRecording.value = true
        maxAcceleration.value = 0f  // Reset maxAcceleration
        recordingDuration.value = 0f  // Reset recordingDuration
        initTempFusionFile()
    }

    /**
     * Initializes a temporary file for fusion data recording.
     */
    private fun initTempFusionFile() {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        fileDirectory.mkdirs()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val tempFileName = "temp_fusion_data_${dateFormat.format(Date(startTime))}.csv"
        tempFusionFile = File(fileDirectory, tempFileName)

        fusionFileWriter = FileWriter(tempFusionFile, true)
        fusionFileWriter?.append("\"Time (s)\",\"Fused x\",\"Fused y\",\"Fused z\"\n")
    }

    /**
     * Renames the temporary fusion file to a final file name with sanitized club name and direction tag.
     */
    private fun renameTempFusionFile() {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

        // Sanitize the club name by removing spaces and special characters
        val sanitizedClubName = selectedClub.value.replace("\\s".toRegex(), "_").replace("[^a-zA-Z0-9_]".toRegex(), "")
        val fileName = "fusion_data_${sanitizedClubName}_${flightDirectionTag}_${dateFormat.format(Date(startTime))}.csv"
        val finalFile = File(fileDirectory, fileName)

        if (tempFusionFile?.renameTo(finalFile) == true) {
            recordingDuration.value = getLastTimestampFromFile(finalFile)
        } else {
            // Handle error if renaming fails
            println("Fehler beim Umbenennen der Datei")
        }
    }

    /**
     * Gets the last timestamp from a file.
     *
     * @param file The file to read the timestamp from.
     * @return The last timestamp value in the file.
     */
    private fun getLastTimestampFromFile(file: File): Float {
        var lastTimestamp = 0f
        if (file.exists()) {
            BufferedReader(FileReader(file)).use { reader ->
                reader.readLine() // Skip header
                reader.forEachLine { line ->
                    val parts = line.split(",")
                    if (parts.isNotEmpty()) {
                        lastTimestamp = parts[0].toFloat()
                    }
                }
            }
        }
        return lastTimestamp
    }

    /**
     * Stops recording fusion data.
     * Closes the file writer and renames the temporary file to the final name.
     */
    private fun stopFusionRecording() {
        isFusionRecording.value = false
        closeFusionFileWriter()
        renameTempFusionFile()
    }

    /**
     * Saves the results of a direction training session to a CSV file.
     */
    private fun saveDirectionTrainingSessionResults() {
        val folderName = "Documents/TrainingSessions"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs()
        }
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val fileName = "session_${dateFormat.format(Date())}.csv"
        val file = File(fileDirectory, fileName)

        fileWriter = FileWriter(file, true)
        fileWriter?.append("\"Club\",\"Direction\",\"Status\"\n")
        for (result in trainingSessionResults) {
            fileWriter?.append("${result.first},${result.second},${result.third}\n")
        }
        fileWriter?.close()
    }


    // ACCELERATION TRAINER

    /**
     * Loads the results of an acceleration training session from a CSV file.
     *
     * @param fileName The name of the file containing the training session results.
     * @return A list of triples containing the club, target acceleration, and actual acceleration of each entry in the session.
     */
    private fun loadAccelerationSessionResults(fileName: String): List<Triple<String, String, String>> {
        val results = mutableListOf<Triple<String, String, String>>()
        val folderName = "Documents/AccelerationTrainingSessions"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        val file = File(fileDirectory, "$fileName.csv")
        if (file.exists()) {
            BufferedReader(FileReader(file)).use { reader ->
                reader.readLine() // Skip header
                reader.forEachLine { line ->
                    val parts = line.split(",")
                    if (parts.size == 3) {
                        results.add(Triple(parts[0], parts[1], parts[2]))
                    }
                }
            }
        }
        return results
    }

    /**
     * Retrieves the maximum acceleration recorded in the current training session.
     *
     * @return The maximum acceleration value recorded.
     */
    private fun getLastRecordedMaxAcceleration(): Float {
        return if (trainingSessionResults.isNotEmpty()) {
            trainingSessionResults.last().third.toFloatOrNull() ?: 0f
        } else {
            0f
        }
    }

    /**
     * Gets the last generated CSV file from the Documents directory.
     *
     * @return The last generated file, or null if no files are found.
     */
    private fun getLastGeneratedFile(): File? {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        val files = fileDirectory.listFiles()?.filter { it.extension == "csv" }
        return files?.maxByOrNull { it.lastModified() }
    }

    /**
     * Reads a generated CSV file and retrieves the maximum acceleration value from the last column.
     *
     * @param file The file to read the maximum acceleration value from.
     * @return The maximum acceleration value in the file.
     */
    private fun getMaxAccelerationFromFile(file: File): Float {
        var maxAcceleration = 0f
        if (file.exists()) {
            BufferedReader(FileReader(file)).use { reader ->
                reader.readLine() // Skip header
                reader.forEachLine { line ->
                    val parts = line.split(",")
                    if (parts.isNotEmpty()) {
                        val accelerationValue = parts.last().toFloatOrNull()
                        if (accelerationValue != null && accelerationValue > maxAcceleration) {
                            maxAcceleration = accelerationValue
                        }
                    }
                }
            }
        }
        return maxAcceleration
    }

    /**
     * Retrieves the list of acceleration training session files.
     *
     * @return A list of file names (without extensions) of the acceleration training sessions.
     */
    private fun getAccelerationTrainingSessionFiles(): List<String> {
        val folderName = "Documents/AccelerationTrainingSessions"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        return fileDirectory.listFiles()?.map { it.nameWithoutExtension }?.toList() ?: emptyList()
    }

    /**
     * Saves the results of an acceleration training session to a CSV file.
     */
    private fun saveAccelerationTrainingSessionResults() {
        val folderName = "Documents/AccelerationTrainingSessions"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs()
        }
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val fileName = "acceleration_session_${dateFormat.format(Date())}.csv"
        val file = File(fileDirectory, fileName)

        fileWriter = FileWriter(file, true)
        fileWriter?.append("Club,Target Acceleration,Actual Acceleration\n")
        for (result in trainingSessionResults) {
            fileWriter?.append("${result.first},${result.second},${result.third}\n")
        }
        fileWriter?.close()
        isTrainingSession.value = false // Ensure this flag is reset after saving
    }

    /**
     * Stops recording acceleration data from the target sensor.
     * Closes the file writer and updates the session results.
     */
    private fun stopTargetAccelerationSensorRecording() {
        isAccelerometerRecording.value = false
        closeFileWriter()
        // Get the generated file and calculate max acceleration before updating the results
        val file = getLastGeneratedFile()
        if (file != null) {
            maxAcceleration.value = getMaxAccelerationFromFile(file)
        }
        updateAccelerationTrainingSessionResults(selectedClub.value, targetAcceleration.value)
        showTargetAccelerationResultScreen.value = true
    }

    /**
     * Updates the results of an acceleration training session with the latest data.
     *
     * @param club The club used in the session.
     * @param targetAcceleration The target acceleration for the session.
     */
    private fun updateAccelerationTrainingSessionResults(club: String, targetAcceleration: Float) {
        trainingSessionResults.add(Triple(club, targetAcceleration.toString(), maxAcceleration.value.toString()))
        // Reset maxAcceleration for the next swing
        maxAcceleration.value = 0f
    }



    // SINGLE SENSOR RECORDING


    /**
     * Starts the recording of acceleration data.
     * Clears any existing sensor data, sets the start time, and initializes the file writer.
     */
    private fun startAccelerationRecording() {
        sensorData.clear()
        startTime = System.currentTimeMillis()
        isAccelerometerRecording.value = true
        initAccelerationFileWriter()
    }

    /**
     * Stops the recording of acceleration data.
     * Closes the file writer.
     */
    private fun stopAccelerationRecording() {
        isAccelerometerRecording.value = false
        closeFileWriter()
    }

    /**
     * Initializes the file writer for recording acceleration data.
     * Creates the necessary directories and file, and writes the header to the file.
     */
    private fun initAccelerationFileWriter() {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        fileDirectory.mkdirs()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val fileName = "${selectedSensorType.value}_data_${dateFormat.format(Date(startTime))}.csv"
        val file = File(fileDirectory, fileName)

        fileWriter = FileWriter(file, true)
        fileWriter?.append("\"Time (s)\",\"X\",\"Y\",\"Z\"\n")
    }

    /**
     * Starts the recording of gyroscope data.
     * Clears any existing gyroscope data, sets the start time, and initializes the temporary file for recording.
     */
    private fun startGyroscopeRecording() {
        gyroscopeDataList.clear()
        startTime = System.currentTimeMillis()
        isGyroscopeRecording.value = true
        initTempGyroscopeFile()
    }

    /**
     * Stops the recording of gyroscope data.
     * Renames the temporary file to its final name and closes the file writer.
     */
    private fun stopGyroscopeRecording() {
        isGyroscopeRecording.value = false
        renameTempGyroscopeFile()
        closeFileWriter()
    }

    /**
     * Initializes a temporary file for recording gyroscope data.
     * Creates the necessary directories and file, and writes the header to the file.
     */
    private fun initTempGyroscopeFile() {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        fileDirectory.mkdirs()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val tempFileName = "temp_gyroscope_data_${dateFormat.format(Date(startTime))}.csv"
        tempGyroscopeFile = File(fileDirectory, tempFileName)

        fileWriter = FileWriter(tempGyroscopeFile, true)
        fileWriter?.append("\"Time (s)\",\"X\",\"Y\",\"Z\"\n")
    }

    /**
     * Renames the temporary gyroscope data file to its final name.
     */
    private fun renameTempGyroscopeFile() {
        val folderName = "Documents"
        val fileDirectory = File(Environment.getExternalStorageDirectory(), folderName)
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val fileName = "gyroscope_data_${dateFormat.format(Date(startTime))}.csv"
        val finalFile = File(fileDirectory, fileName)

        tempGyroscopeFile?.renameTo(finalFile)
    }


}
