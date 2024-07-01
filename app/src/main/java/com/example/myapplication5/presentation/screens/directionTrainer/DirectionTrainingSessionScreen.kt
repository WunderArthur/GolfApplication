package com.example.golfSwingApplication.presentation.screens.directionTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Direction Training Session screen.
 *
 * This screen provides options to start a new training session, view past training sessions, or go back.
 *
 * @param onStartTrainingSession A callback function to be invoked when the "New Training Session" button is pressed.
 * @param onPastTrainingSessions A callback function to be invoked when the "Past Training Sessions" button is pressed.
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 */
@Composable
fun DirectionTrainingSessionScreen(
    onStartTrainingSession: () -> Unit,
    onPastTrainingSessions: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size
            .background(Color.Black) // Set the background color to black
            .padding(20.dp), // Apply padding around the column
        verticalArrangement = Arrangement.Center, // Center children vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally
    ) {
        StyledButton(
            onClick = onStartTrainingSession, // Trigger the callback to start a new training session
            text = "New Training Session",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        StyledButton(
            onClick = onPastTrainingSessions, // Trigger the callback to view past training sessions
            text = "Past Training Sessions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        StyledButton(
            onClick = onBack, // Trigger the callback to go back
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}
