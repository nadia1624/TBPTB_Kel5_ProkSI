package com.example.proksi_tbptb.frontend.kegiatanTerkirim.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun AnimasiAbsensiTercatat (){
        Row {
            Image(
                painter = painterResource(id = R.drawable.element1),  // Ganti dengan gambar Anda
                contentDescription = "Animasi",
                modifier = Modifier
                    .size(200.dp)
                    .align(
                        Alignment.CenterVertically
                    )
            )
            Column(
                modifier = Modifier
            ){
                Text(
                    text = "Absensi mu", // Teks bisa lebih pendek
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp, // Ukuran font
                        lineHeight = 30.sp // Menambah tinggi baris untuk memastikan dua baris
                    ),
                    modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    text = "sudah tercatat!", // Teks bisa lebih pendek
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp, // Ukuran font
                        lineHeight = 30.sp // Menambah tinggi baris untuk memastikan dua baris
                    ),
                    modifier = Modifier.padding(bottom = 8.dp))
            }
        }
}


@Preview
@Composable
fun PreviewAnimasiAbsenTercatat(){
    AnimasiAbsensiTercatat()
}