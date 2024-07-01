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
 * Composable function to display the Target Acceleration Result Screen.
 *
 * This screen shows the target and maximum acceleration results and provides options to rehit, choose a new club, or end the training session.
 *
 * @param targetAcceleration The target acceleration value to be displayed.
 * @param maxAcceleration The maximum acceleration value achieved to be displayed.
 * @param onRehit A callback function to be invoked when the "Rehit same Golfclub" button is pressed.
 * @param onNewClub A callback function to be invoked when the "Hit Different Golfclub" button is pressed.
 * @param onEndTrainingSession A callback function to be invoked when the "End Training Session" button is pressed.
 */
@Composable
fun TargetAccelerationResultScreen(
    targetAcceleration: Float,
    maxAcceleration: Float,
    onRehit: () -> Unit,
    onNewClub: () -> Unit,
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
        Text(
            text = "Target Acceleration Result", // Display the heading text.
            color = Color.White, // Set the text color to white.
            fontSize = 11.sp, // Set the font size to 11 sp.
            modifier = Modifier.padding(bottom = 2.dp) // Apply bottom padding.
        )

        Text(
            text = "Target Acceleration: $targetAcceleration m/s²", // Display the target acceleration.
            color = Color.White, // Set the text color to white.
            fontSize = 7.sp, // Set the font size to 7 sp.
            modifier = Modifier.padding(bottom = 2.dp) // Apply bottom padding.
        )

        Text(
            text = "Max Acceleration: ${maxAcceleration} m/s²", // Display the maximum acceleration.
            color = Color.White,
            fontSize = 7.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        StyledButton(
            onClick = onRehit, // Invoke the onRehit callback when clicked.
            text = "Rehit same Golfclub", // Set the button text.
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width.
                .padding(top = 1.dp) // Apply top padding.
        )

        StyledButton(
            onClick = onNewClub, // Invoke the onNewClub callback when clicked.
            text = "Hit Different Golfclub",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp)
        )

        StyledButton(
            onClick = onEndTrainingSession, // Invoke the onEndTrainingSession callback when clicked.
            text = "End Training Session",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp)
        )
    }
}
