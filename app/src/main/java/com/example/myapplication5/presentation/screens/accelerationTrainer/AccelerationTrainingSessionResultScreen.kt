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
 * Composable function to display the results of an acceleration training session.
 *
 * @param results A list of triples containing the club used, target acceleration, and actual acceleration.
 * @param onBack A callback function to be invoked when the back button is pressed.
 */
@Composable
fun AccelerationTrainingSessionResultScreen(
    results: List<Triple<String, String, String>>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size.
            .background(Color.Black) // Set the background color to black.
            .padding(20.dp), // Apply padding around the column.
        verticalArrangement = Arrangement.Top, // Arrange children vertically at the top.
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally.
    ) {
        Text(
            text = "Training Session Results", // Display the heading text.
            color = Color.White, // Set the text color to white.
            fontSize = 16.sp, // Set the font size to 16 sp.
            modifier = Modifier
                .padding(bottom = 8.dp) // Apply bottom padding.
                .align(Alignment.CenterHorizontally) // Center the text horizontally.
        )

        // Iterate through each result and display the details.
        results.forEach { result ->
            Text(
                text = "Club: ${result.first}, Target: ${result.second}, Actual: ${result.third}", // Display the club, target, and actual acceleration.
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        StyledButton(
            onClick = onBack, // Invoke the onBack callback when the button is clicked.
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
}
