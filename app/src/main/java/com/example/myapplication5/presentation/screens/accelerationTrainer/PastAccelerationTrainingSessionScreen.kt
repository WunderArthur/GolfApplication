package com.example.golfSwingApplication.presentation.screens.accelerationTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Past Acceleration Training Sessions screen.
 *
 * This screen provides a list of past acceleration training session files, allowing the user to select a session to view details or navigate back.
 *
 * @param sessionFiles A list of session file names to be displayed.
 * @param onSessionSelected A callback function to be invoked when a session file is selected.
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 */
@Composable
fun PastAccelerationTrainingSessionScreen(
    sessionFiles: List<String>,
    onSessionSelected: (String) -> Unit,
    onBack: () -> Unit
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
            text = "Past Acceleration Training Sessions", // Display the heading text.
            color = Color.White, // Set the text color to white.
            fontSize = 13.sp, // Set the font size to 13 sp.
            modifier = Modifier.padding(bottom = 8.dp) // Apply bottom padding.
        )

        LazyColumn {
            items(sessionFiles) { file ->
                StyledButton(
                    onClick = { onSessionSelected(file) }, // Invoke the onSessionSelected callback when clicked.
                    text = file,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        StyledButton(
            onClick = onBack, // Invoke the onBack callback when clicked.
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}
