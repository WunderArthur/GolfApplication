package com.example.golfSwingApplication.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.Text

@Composable
fun StyledButton(
    onClick: () -> Unit,
    text: String,
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
                fontSize = 13.sp,
                color = Color.Black, // text color is set to black
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
