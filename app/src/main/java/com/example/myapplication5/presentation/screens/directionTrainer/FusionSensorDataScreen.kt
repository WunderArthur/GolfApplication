package com.example.golfSwingApplication.presentation.screens.directionTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Fusion Sensor Data screen.
 *
 * This screen shows the current sensor data and provides an option to stop recording.
 *
 * @param currentSensorData A FloatArray containing the current sensor data values.
 * @param onStopRecording A callback function to be invoked when the "Stop Recording" button is pressed.
 */
@Composable
fun FusionSensorDataScreen(
    currentSensorData: FloatArray,
    onStopRecording: () -> Unit
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
            text = "Sensor Data",
            color = Color.White,
            fontSize = 16.sp, // Smaller font size
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding
        )

        Row(
            modifier = Modifier.fillMaxWidth(), // Make the row fill the available width
            horizontalArrangement = Arrangement.SpaceEvenly // Distribute children evenly within the row
        ) {
            Column(modifier = Modifier.width(60.dp)) {
                Text(
                    text = "X:",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Y:",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Z:",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Column {
                Text(
                    text = "${currentSensorData[1]}",
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${currentSensorData[2]}",
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${currentSensorData[3]}",
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        StyledButton(
            onClick = onStopRecording, // Trigger the callback to stop recording
            text = "Stop Recording",
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width
                .padding(top = 16.dp)
        )
    }
}
