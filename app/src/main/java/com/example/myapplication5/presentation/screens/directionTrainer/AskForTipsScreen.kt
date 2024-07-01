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
 * Composable function to display a screen asking the user if they want tips.
 *
 * This screen shows a message and provides two buttons for the user to respond with "Yes" or "No".
 *
 * @param message The message to be displayed to the user.
 * @param onYes A callback function to be invoked when the "Yes" button is pressed.
 * @param onNo A callback function to be invoked when the "No" button is pressed.
 */
@Composable
fun AskForTipsScreen(
    message: String,
    onYes: () -> Unit,
    onNo: () -> Unit
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
            text = message, // Display the provided message.
            color = Color.White, // Set the text color to white.
            fontSize = 16.sp, // Set the font size to 16 sp.
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding.
        )

        StyledButton(
            onClick = onYes, // Invoke the onYes callback when clicked.
            text = "Yes", // Set the button text.
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width.
                .padding(vertical = 4.dp)
        )

        StyledButton(
            onClick = onNo, // Invoke the onNo callback when clicked.
            text = "No",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}
