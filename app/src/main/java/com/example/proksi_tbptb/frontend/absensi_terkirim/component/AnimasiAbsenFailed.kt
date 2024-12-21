package com.example.proksi_tbptb.frontend.absensi_terkirim.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proksi_tbptb.R

@Composable
fun AnimasiAbsensiFailed (){
    Column {
        Text(
            text = "Ups! Kamu sudah tidak\n" +
                    "bisa mengambil absensi", // Teks bisa lebih pendek
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, // Ukuran font
                lineHeight = 30.sp // Menambah tinggi baris untuk memastikan dua baris
            ),
            modifier = Modifier.padding(bottom = 8.dp))
        Image(
            painter = painterResource(id = R.drawable.element3),  // Ganti dengan gambar Anda
            contentDescription = "Animasi",
            modifier = Modifier
                .size(300.dp)
                .align(
                    Alignment.CenterHorizontally
                )
        )
    }
}


@Preview
@Composable
fun PreviewAnimasiAbsenFailed(){
    AnimasiAbsensiFailed()
}