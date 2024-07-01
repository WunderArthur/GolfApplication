package com.example.golfSwingApplication.presentation.screens.directionTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text

/**
 * Composable function to display the Ball Flight Direction screen.
 *
 * This screen allows the user to select the direction of the ball flight.
 *
 * @param onDirectionSelected A callback function to be invoked when a direction is selected.
 */


@Composable
fun CustomStyledButton(
    onClick: () -> Unit,
    text: String,
    fontSize: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp)) // More rounded corners
            .background(Color(0xFFE24D08)) // Orange color
            .clickable { onClick() }
            .padding(vertical = 9.dp, horizontal = 12.dp) // Less height
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize.sp, // Set custom font size
                color = Color.Black, // Text color is set to black
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun BallFlightDirectionScreen(
    onDirectionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available size.
            .background(Color.Black) // Set the background color to black.
            .padding(20.dp), // Apply padding around the column.
        verticalArrangement = Arrangement.Center, // Center children vertically.
        horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally.
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp), // Set spacing between the buttons.
            modifier = Modifier.fillMaxWidth() // Make the row fill the available width.
        ) {
            CustomStyledButton(
                onClick = { onDirectionSelected("left") }, // Invoke callback with "straight" when clicked.
                text = "Left",
                fontSize = 11, // Set custom font size
                modifier = Modifier
                    .weight(1.1f)
                    .height(60.dp)
                    .width(120.dp)
            )

            CustomStyledButton(
                onClick = { onDirectionSelected("straight") }, // Invoke callback with "straight" when clicked.
                text = "Straight",
                fontSize = 11, // Set custom font size
                modifier = Modifier
                    .weight(1.5f)
                    .height(60.dp)
                    .width(120.dp)
            )

            CustomStyledButton(
                onClick = { onDirectionSelected("right") }, // Invoke callback with "straight" when clicked.
                text = "Right",
                fontSize = 11, // Set custom font size
                modifier = Modifier
                    .weight(1.2f)
                    .height(60.dp)
                    .width(120.dp)
            )
        }
    }
}
