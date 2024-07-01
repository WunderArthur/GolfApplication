package com.example.golfSwingApplication.presentation.screens.directionTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.golfSwingApplication.presentation.theme.StyledButton
/**
 * Composable function to display the Past Direction Training Session screen.
 *
 * This screen lists past training session files and provides options to select a session or go back.
 *
 * @param sessionFiles A list of strings representing the session file names.
 * @param onSessionSelected A callback function to be invoked when a session is selected.
 * @param onBack A callback function to be invoked when the "Back" button is pressed.
 */
@Composable
fun PastDirectionTrainingSessionScreen(
    sessionFiles: List<String>,
    onSessionSelected: (String) -> Unit,
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
        Text(
            text = "Past Training Sessions",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(sessionFiles) { file ->
                Text(
                    text = file,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onSessionSelected(file) }
                )
            }
        }

        StyledButton(
            onClick = onBack, // Trigger the callback to go back
            text = "Back",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}
