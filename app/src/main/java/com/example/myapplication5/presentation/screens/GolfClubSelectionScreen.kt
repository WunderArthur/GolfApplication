package com.example.golfSwingApplication.presentation.screens

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.golfSwingApplication.presentation.theme.StyledButton

/**
 * Composable function to display the Golf Club Selection Screen.
 *
 * This screen allows the user to select a golf club from a predefined list and provides an option to go back.
 *
 * @param onClubSelected A callback function to be invoked when a club is selected, passing the selected club as a string.
 * @param onBack A callback function to be invoked when the "Back" button is pressed or a horizontal drag gesture is detected.
 */
@Composable
fun GolfClubSelectionScreen(onClubSelected: (String) -> Unit, onBack: () -> Unit) {
    // List of golf clubs to display
    val golfClubs = listOf(
        "Driver", "3 Wood", "5 Wood", "7 Wood", "Hybrid",
        "1 Iron", "2 Iron", "3 Iron", "4 Iron", "5 Iron",
        "6 Iron", "7 Iron", "8 Iron", "9 Iron", "Pitching Wedge",
        "52 Degree", "54 Degree", "56 Degree", "58 Degree", "60 Degree"
    )

    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size
            .pointerInput(Unit) {
                // Detect horizontal drag gestures to trigger the onBack callback
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > 0) {
                        onBack()
                    }
                }
            }
            .padding(16.dp), // Apply padding around the column
        verticalArrangement = Arrangement.Center, // Center children vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space items
        ) {
            items(golfClubs.size) { index ->
                StyledButton(
                    onClick = { onClubSelected(golfClubs[index]) }, // Trigger the callback with the selected club
                    text = golfClubs[index],
                    modifier = Modifier.fillMaxWidth() // Make the button fill the available width
                )
            }
        }
        StyledButton(
            onClick = onBack, // Trigger the callback to go back
            text = "Back",
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width
                .padding(top = 16.dp)
        )
    }
}
