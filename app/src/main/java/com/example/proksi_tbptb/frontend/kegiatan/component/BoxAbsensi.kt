package com.example.proksi_tbptb.frontend.kegiatan.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun BoxAbsensi(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "Kegiatan 1",
    status: Int = 0,  // Menambahkan status untuk menentukan kondisi (0: Belum diisi, 1: Sedang berlangsung, 2: Sudah diisi)
    painter: Painter = painterResource(id = R.drawable.ceklis),
    waktu: String = "hh:mm - hh:mm", // Waktu yang ditampilkan jika sedang berlangsung
    tanggal: String = "dd/mm/yyyy" // Tanggal yang ditampilkan jika sedang berlangsung
) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Make the box fill the width
            .height(70.dp)
            .border(1.5.dp, Color.Black, RoundedCornerShape(14.dp)) // Add border with rounded corners
            .clickable { onClick() } // Make the box clickable
            .background(
                when (status) {
                    1 -> Color(0xFFF5E7C6) // Sedang berlangsung
                    2 -> Color(0xFFF5E7C6) // Sudah diisi
                    else -> Color(0xFFFF6D1F) // Tidak disii
                },
                RoundedCornerShape(14.dp)
            ) // Background dengan kondisi
    ) {
        when (status) {
            1 -> { // Kondisi sedang berlangsung
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                    ){
                        Text(text = text)
                        Spacer(modifier = Modifier.width(148.dp))
                        Column(
                            modifier = Modifier
                        ){
                            Text(text = "$waktu", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                            Text(text = "     $tanggal", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            2 -> { // Kondisi sudah diisi
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                    ){
                        Text(text = text)
                        Spacer(modifier = Modifier.width(208.dp))
                        Image(
                            painter = painter,
                            contentDescription = "Ceklis",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
            }
            else -> { // Kondisi tidak diisi
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                    ){
                        Text(text = "$text")
                        Spacer(modifier = Modifier.width(158.dp))
                        Text(text = "Tidak Selesai")
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun BoxAbsensiPreview() {
    BoxAbsensi()
}
