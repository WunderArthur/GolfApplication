package com.example.golfSwingApplication.presentation.screens.directionTrainer

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
 * Composable function to display the Direction Sensor Recording Summary screen.
 *
 * This screen shows a summary of the recording, including maximum acceleration and recording duration.
 *
 * @param maxAcceleration The maximum acceleration recorded during the session.
 * @param recordingDuration The total duration of the recording session.
 * @param onBack A callback function to be invoked when the "Rehit same Golfclub" button is pressed.
 * @param onGolfSelectionScreen A callback function to be invoked when the "Hit Different Golfclub" button is pressed.
 * @param onEndTrainingSession A callback function to be invoked when the "End Training Session" button is pressed.
 */
@Composable
fun DirectionSensorRecordingSummaryScreen(
    maxAcceleration: Float,
    recordingDuration: Float,
    onBack: () -> Unit,
    onGolfSelectionScreen: () -> Unit,
    onEndTrainingSession: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size.
            .background(Color.Black) // Set the background color to black.
            .padding(10.dp), // Apply padding around the column.
        verticalArrangement = Arrangement.Center, // Center children vertically.
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally.
    ) {
        Text(
            text = "Recording Summary",
            color = Color.White,
            fontSize = 13.sp, // Set font size to 13sp.
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding.
        )

        Text(
            text = "Max Acceleration: ${maxAcceleration} m/s²",
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Text(
            text = "Recording Duration: ${recordingDuration} s",
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        StyledButton(
            onClick = onBack, // Invoke the onBack callback when clicked.
            text = "Rehit same Golfclub",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        StyledButton(
            onClick = onGolfSelectionScreen, // Invoke the onGolfSelectionScreen callback when clicked.
            text = "Hit Different Golfclub",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        StyledButton(
            onClick = onEndTrainingSession, // Invoke the onEndTrainingSession callback when clicked.
            text = "End Training Session",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}
