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
 * Composable function to display the Tip Screen.
 *
 * This screen shows a tip text and provides options to see another tip or continue training.
 *
 * @param tipText The tip text to display.
 * @param onAnotherTip A callback function to be invoked when the "Another Tip" button is pressed. This parameter is optional.
 * @param onContinueTraining A callback function to be invoked when the "Continue Training" button is pressed.
 */
@Composable
fun TipScreen(
    tipText: String,
    onAnotherTip: (() -> Unit)? = null,
    onContinueTraining: () -> Unit
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
            text = tipText,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Conditionally display the "Another Tip" button if the callback is provided
        onAnotherTip?.let {
            StyledButton(
                onClick = it, // Trigger the callback for another tip
                text = "Another Tip",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        StyledButton(
            onClick = onContinueTraining, // Trigger the callback to continue training
            text = "Continue Training",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}
