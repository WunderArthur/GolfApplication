package com.example.golfSwingApplication.presentation.screens.directionTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Direction Sensor Selection screen.
 *
 * This screen allows the user to start or stop recording a swing, navigate back to the golf club selection,
 * and end the training session.
 *
 * @param handPositionData A list of hand position data represented as arrays of floats.
 * @param onBack A callback function to be invoked when the "Back to Golf Club Selection" button is pressed.
 * @param onStartRecording A callback function to be invoked when the "Start Swing" button is pressed.
 * @param onStopRecording A callback function to be invoked when the "End Swing" button is pressed.
 * @param onStopRecordingAndNavigate A callback function to be invoked when the "End Swing" button is pressed and navigate.
 * @param isRecording A boolean indicating if the recording is currently in progress.
 * @param onEndTrainingSession A callback function to be invoked when the "End Training Session" button is pressed.
 */
@Composable
fun DirectionSensorSelectionScreen(
    handPositionData: List<FloatArray>,
    onBack: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onStopRecordingAndNavigate: () -> Unit,
    isRecording: Boolean,
    onEndTrainingSession: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size.
            .background(Color.Black) // Set the background color to black.
            .padding(20.dp), // Apply padding around the column.
        verticalArrangement = Arrangement.Center, // Center children vertically.
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally.
    ) {
        if (isRecording) {
            // Button to stop recording and navigate
            StyledButton(
                onClick = onStopRecordingAndNavigate,
                text = "End Swing",
                modifier = Modifier.fillMaxWidth() // Make the button fill the available width.
            )
        } else {
            // Button to start recording
            StyledButton(
                onClick = onStartRecording,
                text = "Start Swing",
                modifier = Modifier.fillMaxWidth() // Make the button fill the available width.
            )
        }

        // Button to go back to golf club selection
        StyledButton(
            onClick = onBack,
            text = "Back to Golf Club Selection",
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width.
                .padding(top = 12.dp) // Apply top padding.
        )

        // Button to end the training session
        StyledButton(
            onClick = onEndTrainingSession,
            text = "End Training Session",
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width.
                .padding(top = 12.dp) // Apply top padding.
        )
    }
}
