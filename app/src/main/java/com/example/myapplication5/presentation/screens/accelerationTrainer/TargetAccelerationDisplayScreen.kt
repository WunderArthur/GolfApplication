package com.example.golfSwingApplication.presentation.screens.accelerationTrainer

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
 * Composable function to display the Target Acceleration Display Screen.
 *
 * This screen shows the current sensor data for target acceleration and provides controls to start or stop recording.
 *
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 * @param onStartRecording A callback function to be invoked when the "Start Recording" button is pressed.
 * @param onStopRecording A callback function to be invoked when the "Stop Recording" button is pressed.
 * @param isRecording A boolean indicating whether recording is currently in progress.
 * @param currentSensorData An array containing the current sensor data.
 */
@Composable
fun TargetAccelerationDisplayScreen(
    onBack: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    isRecording: Boolean,
    currentSensorData: FloatArray
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size.
            .background(Color.Black) // Set the background color to black.
            .padding(20.dp), // Apply padding around the column.
        verticalArrangement = Arrangement.Center, // Center children vertically.
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally.
    ) {
        Text(
            text = "Target Acceleration Data", // Display the heading text.
            color = Color.White, // Set the text color to white.
            fontSize = 13.sp, // Set the font size to 13 sp.
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding.
        )

        Column(
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding to the inner column.
        ) {
            Text(
                text = "X: ${currentSensorData[1]}", // Display the X-axis sensor data.
                color = Color.White, // Set the text color to white.
                fontSize = 10.sp, // Set the font size to 10 sp.
                modifier = Modifier.padding(bottom = 2.dp) // Apply bottom padding.
            )
            Text(
                text = "Y: ${currentSensorData[2]}", // Display the Y-axis sensor data.
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Z: ${currentSensorData[3]}", // Display the Z-axis sensor data.
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }

        if (isRecording) {
            StyledButton(
                onClick = onStopRecording, // Invoke the onStopRecording callback when clicked.
                text = "Stop Recording", // Set the button text.
                modifier = Modifier.fillMaxWidth() // Make the button fill the available width.
            )
        } else {
            StyledButton(
                onClick = onStartRecording, // Invoke the onStartRecording callback when clicked.
                text = "Start Recording",
                modifier = Modifier.fillMaxWidth()
            )
        }

        StyledButton(
            onClick = onBack, // Invoke the onBack callback when clicked.
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp) // Apply top padding.
        )
    }
}
