package com.example.golfSwingApplication.presentation.screens.accelerationTrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.compose.ui.text.style.TextAlign
import com.example.golfSwingApplication.presentation.theme.StyledButton

/**
 * Composable function to display the Target Acceleration Screen.
 *
 * This screen allows the user to select the target acceleration for a specific golf club.
 *
 * @param golfClub The name of the golf club for which the target acceleration is being set.
 * @param onAccelerationSelected Callback function invoked when the target acceleration is selected.
 * @param onBack Callback function invoked when the back button is pressed.
 * @param onNext Callback function invoked when the next button is pressed.
 */
@Composable
fun TargetAccelerationScreen(
    golfClub: String,
    onAccelerationSelected: (Float) -> Unit,
    onBack: () -> Unit,
    onNext: (Float) -> Unit
) {
    var hundredValue by remember { mutableStateOf(0) }
    var tenValue by remember { mutableStateOf(0) }
    var unitValue by remember { mutableStateOf(0) }

    // Calculate the target acceleration based on the selected values
    val targetAcceleration = (hundredValue * 100 + tenValue * 10 + unitValue).toFloat()

    // Layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title text
        Text(
            text = "Select Target Acceleration for $golfClub",
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Row layout for the number pickers
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                value = hundredValue,
                range = 0..9,
                onValueChange = { hundredValue = it },
                modifier = Modifier.size(40.dp, 60.dp) // Adjusted height to match picker size
            )
            NumberPicker(
                value = tenValue,
                range = 0..9,
                onValueChange = { tenValue = it },
                modifier = Modifier.size(40.dp, 60.dp) // Adjusted height to match picker size
            )
            NumberPicker(
                value = unitValue,
                range = 0..9,
                onValueChange = { unitValue = it },
                modifier = Modifier.size(40.dp, 60.dp) // Adjusted height to match picker size
            )
        }

        // Display the selected target acceleration
        Text(
            text = "$targetAcceleration m/s²",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Button to proceed to the next screen
        StyledButton(
            onClick = { onNext(targetAcceleration) },
            text = "Next",
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
    }
}

/**
 * Composable function to display a number picker.
 *
 * This function allows the user to pick a number within a specified range by scrolling.
 *
 * @param value The currently selected value.
 * @param range The range of values that can be selected.
 * @param onValueChange Callback function invoked when the value changes.
 * @param modifier Modifier to be applied to the number picker.
 */
@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Convert the range to a list of values
    val values = range.toList()
    val index = values.indexOf(value)

    // LazyColumn to display the values in a scrollable list
    LazyColumn(
        modifier = modifier
            .size(40.dp, 80.dp) // Adjusted height to show fewer numbers
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(values) { i, v ->
            Text(
                text = v.toString(),
                color = if (i == index) Color.White else Color.LightGray,
                fontSize = if (i == index) 20.sp else 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 2.dp) // Reduced padding to fit within smaller size
                    .background(if (i == index) Color.DarkGray else Color.Transparent)
                    .fillMaxWidth()
                    .clickable {
                        onValueChange(v)
                    }
            )
        }
    }
}
