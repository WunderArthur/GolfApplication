package com.example.golfSwingApplication.presentation.screens.directionTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.golfSwingApplication.presentation.theme.StyledButton

/**
 * A composable function that displays the results of a training session.
 *
 * @param results A list of triples where each triple contains the club used, the direction of the ball, and the status of the swing.
 * @param onBack A callback function that is invoked when the back button is pressed.
 * @author arthurwunder
 */
@Composable
fun DirectionTrainingSessionResultScreen(
    results: List<Triple<String, String, String>>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the maximum size of the parent
            .background(Color.Black) // Set background color to black
            .padding(20.dp) // Add padding around the column
            .verticalScroll(rememberScrollState()), // Enable vertical scrolling
        verticalArrangement = Arrangement.Center, // Center the content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
    ) {
        Text(
            text = "Training Session Results", // Title text
            color = Color.White, // Set text color to white
            fontSize = 14.sp, // Set font size to 14 sp
            modifier = Modifier.padding(bottom = 8.dp) // Add padding below the text
        )

        // Loop through each result and display it in a row
        for (result in results) {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Fill the maximum width of the parent
                    .padding(bottom = 4.dp) // Add padding below the row
            ) {
                Text(
                    text = result.first, // Display the first element of the triple (club used)
                    color = Color.White, // Set text color to white
                    fontSize = 12.sp, // Set font size to 12 sp
                    modifier = Modifier.weight(1f) // Distribute space evenly
                )
                Text(
                    text = result.second, // Display the second element of the triple (direction of the ball)
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = result.third, // Display the third element of the triple (status of the swing)
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Button to navigate back to the main menu
        StyledButton(
            onClick = onBack, // Callback function when the button is clicked
            text = "Back to Main Menu", // Button text
            modifier = Modifier
                .fillMaxWidth() // Fill the maximum width of the parent
                .padding(top = 16.dp) // Add padding above the button
        )
    }
}
