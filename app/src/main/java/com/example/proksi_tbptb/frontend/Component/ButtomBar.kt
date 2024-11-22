package com.example.proksi_tbptb.frontend.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    onHomeClick: () -> Unit = {},
    onPaperClick: () -> Unit = {},
    onTaskClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(Color(0xFFF9B683)), // Background color for the button bar
        horizontalArrangement = Arrangement.SpaceAround, // Distribute items evenly
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Icon
        IconButton(
            onClick = onHomeClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = Color(0xFF222222) // Customize color
            )
        }
        // Paper Icon
        IconButton(
            onClick = onPaperClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange, // Use a different icon
                contentDescription = "Paper",
                tint = Color(0xFF222222) // Customize color
            )
        }

        // Task Icon
        IconButton(
            onClick = onTaskClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Menu, // Task icon
                contentDescription = "Task",
                tint = Color(0xFF222222) // Customize color
            )
        }

        // Profile Icon
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color(0xFF222222) // Customize color
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar()
}

