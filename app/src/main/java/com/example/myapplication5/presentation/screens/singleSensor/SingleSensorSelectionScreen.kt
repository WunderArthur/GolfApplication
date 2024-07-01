// SingleSensorSelectionScreen.kt

package com.example.golfSwingApplication.presentation.screens.singleSensor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Single Sensor Selection Screen.
 *
 * This screen allows the user to select between different sensors and provides an option to go back.
 *
 * @param onSelectSensor A callback function to be invoked when a sensor is selected, passing the sensor type as a string.
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 */
@Composable
fun SingleSensorSelectionScreen(
    onSelectSensor: (String) -> Unit,
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
            onClick = { onSelectSensor("Accelerometer") }, // Trigger the callback with "Accelerometer"
            text = "Accelerometer",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        StyledButton(
            onClick = { onSelectSensor("Gyroscope") }, // Trigger the callback with "Gyroscope"
            text = "Gyroscope",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        StyledButton(
            onClick = onBack, // Trigger the callback to go back
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}
