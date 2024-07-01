package com.example.golfSwingApplication.presentation.screens.singleSensor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Gyroscope Data Screen.
 *
 * This screen shows the current gyroscope data and provides options to start/stop recording.
 *
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 * @param onStartRecording A callback function to be invoked when the "Start Recording" button is pressed.
 * @param onStopRecording A callback function to be invoked when the "Stop Recording" button is pressed.
 * @param isRecording A boolean indicating whether the recording is currently active.
 * @param currentSensorData A float array containing the current sensor data.
 */
@Composable
fun GyroscopeDataScreen(
    onBack: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    isRecording: Boolean,
    currentSensorData: FloatArray
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size
            .background(Color.Black) // Set the background color to black
            .padding(20.dp), // Apply padding around the column
        verticalArrangement = Arrangement.Center, // Center children vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally
    ) {
        Text(
            text = "Gyroscope Data",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding
        )

        Column(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "X: ${currentSensorData[1]}",
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Y: ${currentSensorData[2]}",
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Z: ${currentSensorData[3]}",
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }

        if (isRecording) {
            StyledButton(
                onClick = onStopRecording, // Trigger the callback to stop recording
                text = "Stop Recording",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            StyledButton(
                onClick = onStartRecording, // Trigger the callback to start recording
                text = "Start Recording",
                modifier = Modifier.fillMaxWidth()
            )
        }

        StyledButton(
            onClick = onBack, // Trigger the callback to go back
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}
