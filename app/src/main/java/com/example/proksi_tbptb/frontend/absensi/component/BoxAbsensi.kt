package com.example.proksi_tbptb.frontend.absensi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.R

@Composable
fun BoxAbsensi(modifier: Modifier = Modifier,
               onClick: () -> Unit = {},
               text: String = "Absensi 1",
               painter: Painter = painterResource(id = R.drawable.ceklis) ) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Make the box fill the width
            .height(100.dp)
            .padding(16.dp) // Add padding around the box
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)) // Add border with rounded corners
            .clickable { onClick() } // Make the box clickable
            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)) // Background with rounded corners

    ) {
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
        )
        Image(
            painter = painter,
            contentDescription = "Ceklis",
            modifier = modifier
                .size(48.dp) // Ukuran gambar
                .align(Alignment.CenterEnd) // Posisikan di kanan tengah
                .padding(end = 16.dp) // Tambahkan padding di kanan
        )
    }
}

@Preview
@Composable
fun BoxAbsensiPreview() {
    BoxAbsensi()
}
