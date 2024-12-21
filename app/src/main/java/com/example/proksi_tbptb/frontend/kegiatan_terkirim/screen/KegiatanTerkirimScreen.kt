package com.example.proksi_tbptb.frontend.kegiatan_terkirim.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.frontend.absensi_terkirim.component.AnimasiAbsensiTercatat
import com.example.proksi_tbptb.frontend.absensi_terkirim.component.BuktiFoto
import com.example.proksi_tbptb.frontend.absensi_terkirim.component.HeaderAbsensiTerkirim
import com.example.proksi_tbptb.frontend.component.TopBar


@Composable
fun KegiatanTerkirimScreen (
    modifier: Modifier = Modifier,
    idRekapan: Int,
){
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
                HeaderAbsensiTerkirim(titleText = "Rapat Global 1")

                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center, // Menempatkan isi ini di tengah secara vertikal
                    horizontalAlignment = Alignment.CenterHorizontally // Menempatkan konten ini di tengah secara horizontal
                ) {
                    Column(
                        modifier = Modifier.width(300.dp),
                    ){
                        Text(
                            text = "Keterangan",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(300.dp)
                                .height(100.dp)
                                .border(1.5.dp, Color.Black, RoundedCornerShape(16.dp))
                        ){
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
//                    BuktiFoto()
                    Spacer(modifier = Modifier.height(16.dp)) // Jarak antara BuktiFoto dan Animasi
                    AnimasiAbsensiTercatat()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewKegiatanTerkirim (){
//    KegiatanTerkirimScreen()
}