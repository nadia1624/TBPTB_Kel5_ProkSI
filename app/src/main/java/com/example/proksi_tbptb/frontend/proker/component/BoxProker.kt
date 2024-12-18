package com.example.proksi_tbptb.frontend.proker.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoxProker(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    status: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Make the box fill the width
            .height(100.dp)
            .padding(16.dp) // Add padding around the box
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)) // Add border with rounded corners
            .clickable { onClick() } // Make the box clickable
            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)) // Background with rounded corners
    ) {
        // Nama Program Kerja
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )

        // Kotak Status Program Kerja dengan warna latar yang berubah sesuai status
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd) // Align status box to the right
                .padding(end = 16.dp, bottom = 8.dp)
                .background(
                    color = when (status) {
                        "Done" -> Color(0xFF388E3C) // Dark Green for Done
                        "In Progress" -> Color(0xFFFBC02D) // Dark Yellow for In Progress
                        "Not Started" -> Color(0xFFD32F2F) // Dark Red for Not Started
                        else -> Color(0xFF9E9E9E) // Default gray for unknown status
                    },
                    shape = RoundedCornerShape(16.dp) // Round the corners of the status box
                )
                .padding(horizontal = 12.dp, vertical = 6.dp) // Padding inside the status box
        ) {
            // Teks status dengan warna putih
            Text(
                text = status,
                color = Color.Black, // Change text color to white
                modifier = Modifier.align(Alignment.Center) // Center the status text inside the box
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBoxProker() {
    BoxProker(
        text = "Seminar Hasil",
        status = "Done"
    )
}


