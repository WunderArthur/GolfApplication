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
 * Composable function to display the Acceleration Training Session screen.
 *
 * This screen provides options to start a new acceleration training session, view past sessions, or navigate back.
 *
 * @param onStartAccelerationTraining A callback function to be invoked when the "Start New Session" button is pressed.
 * @param onPastAccelerationSessions A callback function to be invoked when the "View Past Sessions" button is pressed.
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 */
@Composable
fun AccelerationTrainingSessionScreen(
    onStartAccelerationTraining: () -> Unit,
    onPastAccelerationSessions: () -> Unit,
    onBack: () -> Unit
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
            text = "Acceleration Training Session", // Display the heading text.
            color = Color.White, // Set the text color to white.
            fontSize = 13.sp, // Set the font size to 13 sp.
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding.
        )

        // Button to start a new acceleration training session.
        StyledButton(
            onClick = onStartAccelerationTraining, // Invoke the onStartAccelerationTraining callback when clicked.
            text = "Start New Session",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        // Button to view past acceleration training sessions.
        StyledButton(
            onClick = onPastAccelerationSessions, // Invoke the onPastAccelerationSessions callback when clicked.
            text = "View Past Sessions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        // Button to navigate back.
        StyledButton(
            onClick = onBack, // Invoke the onBack callback when clicked.
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}
