package com.example.proksi_tbptb.frontend.all_proker.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.proksi_tbptb.R

@Composable
fun ProkerCard(
    title: String,
    divisi: String,
    gambar: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = cardColors(
            containerColor = Color(0xFFF5E7C6)) // Warna latar belakang Card

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFF5E7C6))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Pastikan Row menggunakan seluruh lebar
                    .padding(horizontal = 8.dp), // Opsional: menambahkan padding
                horizontalArrangement = Arrangement.SpaceBetween, // Mengatur teks di kiri dan kanan
                verticalAlignment = Alignment.CenterVertically // Menyejajarkan secara vertikal
            ) {
                Text(
                    text = title,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = divisi,
                    color = Color.Black
                )

            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))

            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = gambar),  // Ganti dengan gambar Anda
                    contentDescription = "Upload Icon", // Ukuran gambar
                    modifier = Modifier
                        .fillMaxWidth() // Lebar gambar
                        .height(200.dp) // Tinggi gambar
                        .padding(8.dp) // Opsional: Tambahkan padding jika diperlukan
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }

}

@Preview
@Composable
fun ProkerCardPreview(){
//    ProkerCard(title = "Semhas",
//        divisi = "PSDM ",
//        onClick = {})
}

