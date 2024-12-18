package com.example.proksi_tbptb.frontend.detail_proker.component

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
import androidx.compose.ui.unit.sp

@Composable
fun BoxDetailProker(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    date: String // Parameter baru untuk tanggal
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp) // Add padding around the box
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)) // Add border with rounded corners
            .clickable { onClick() } // Make the box clickable
            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)) // Background with rounded corners
    ) {
        // Nama program kerja
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
        )

        // Tanggal di sebelah kanan
        Text(
            text = date,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun BoxDetailProkerPreview() {
    BoxDetailProker(
        text = "Seminar Hasil 1",
        date = "27 Nov 2024", // Contoh tanggal
        onClick = { println("Preview item diklik") }
    )
}
