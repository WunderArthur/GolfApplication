package com.example.golfSwingApplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Main Menu Screen.
 *
 * This screen provides options to navigate to the Direction Trainer, Acceleration Trainer, and Single Sensor Data screens.
 *
 * @param onHandPosition A callback function to be invoked when the "Direction Trainer" button is pressed.
 * @param onAccelerationTrainer A callback function to be invoked when the "Acceleration Trainer" button is pressed.
 * @param onSingleSensor A callback function to be invoked when the "Single Sensor Data" button is pressed.
 */
@Composable
fun MainMenuScreen(
    onHandPosition: () -> Unit,
    onAccelerationTrainer: () -> Unit,
    onSingleSensor: () -> Unit
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
            onClick = onHandPosition, // Trigger the callback to navigate to the Direction Trainer
            text = "Direction Trainer",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        )

        StyledButton(
            onClick = onAccelerationTrainer, // Trigger the callback to navigate to the Acceleration Trainer
            text = "Acceleration Trainer",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        )

        StyledButton(
            onClick = onSingleSensor, // Trigger the callback to navigate to the Single Sensor Data screen
            text = "Single Sensor Data",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        )
    }
}
