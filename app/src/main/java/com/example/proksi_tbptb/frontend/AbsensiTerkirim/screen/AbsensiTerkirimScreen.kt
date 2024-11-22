package com.example.proksi_tbptb.frontend.AbsensiTerkirim.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.AnimasiAbsensiTercatat
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.BuktiFoto
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.HeaderAbsensiTerkirim
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar


@Composable
fun AbsensiTerkirimScreen (modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF3E1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp)
        ) {
            TopBar(pageTitle = "Absensi") // TopBar tetap di atas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderAbsensiTerkirim(titleText = "Minggu Ke-1")

                Column(
                    modifier = Modifier
                        .weight(1f) // Memberikan ruang agar konten di bawah header tetap berada di tengah
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center, // Menempatkan isi ini di tengah secara vertikal
                    horizontalAlignment = Alignment.CenterHorizontally // Menempatkan konten ini di tengah secara horizontal
                ) {
                    BuktiFoto()
                    Spacer(modifier = Modifier.height(16.dp)) // Jarak antara BuktiFoto dan Animasi
                    AnimasiAbsensiTercatat()
                }
            }
        }
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Menempatkan di bagian bawah layar
        )
    }
}

@Preview
@Composable
fun PreviewAbsensiTerkirim (){
    AbsensiTerkirimScreen()
}