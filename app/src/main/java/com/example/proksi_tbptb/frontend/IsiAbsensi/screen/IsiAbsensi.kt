package com.example.proksi_tbptb.frontend.IsiAbsensi.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.IsiAbsensi.component.AnimasiAbsensi
import com.example.proksi_tbptb.frontend.IsiAbsensi.component.ButtonKirimAbsensi
import com.example.proksi_tbptb.frontend.IsiAbsensi.component.HeaderIsiAbsensi
import com.example.proksi_tbptb.frontend.IsiAbsensi.component.UploadGambarAbsensi

@Composable
fun IsiAbsensi (modifier: Modifier = Modifier){
    Box(
        modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFFAF3E1))
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp)
        ){
            TopBar(pageTitle = "Absensi")
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                HeaderIsiAbsensi(titleText = "Minggu Ke-1")
                UploadGambarAbsensi()
                ButtonKirimAbsensi(
                    text = "Kirim",
                    onClick = { println("Kirim Absen diklik") },
                    buttonColor = Color(0xFFFF6E1F),  // Warna latar belakang
                    contentColor = Color.White      // Warna teks
                )
                AnimasiAbsensi()


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
fun IsiAbsensiPreview(){
    IsiAbsensi()
}