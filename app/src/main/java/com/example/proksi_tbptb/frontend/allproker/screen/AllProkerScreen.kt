package com.example.proksi_tbptb.frontend.allproker.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.Proker.component.CustomButtonProker
import com.example.proksi_tbptb.frontend.absensi.component.BoxAbsensi
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonAbsensi
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonWithIcon
import com.example.proksi_tbptb.frontend.allproker.component.CustomButtonAllProker
import com.example.proksi_tbptb.frontend.allproker.component.ProkerCard

@Composable
fun AllProker(modifier: Modifier = Modifier, navController: NavHostController){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF3E1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp) // Memberikan ruang untuk BottomBar
        ) {
            // TopBar di bagian atas
            TopBar(pageTitle = "All Proker")

            // Konten utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CustomButtonAllProker(
                        text = "Divisi",
                        onClick = { println("Divisi diklik") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF5E7C6),
                        contentColor = Color.Black
                    )
                    CustomButtonAllProker(
                        text = "All",
                        onClick = { println("All diklik") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF9B683),
                        contentColor = Color.Black
                    )
                }
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp))
                {
                    items(10) { index -> // Mengulang daftar 10 item sebagai contoh
                        ProkerCard(
                            title = "Proker ${index + 1}",
                            divisi = "Divisi ${index + 1}",
                            onClick = { println("Proker ${index + 1} diklik") }
                        )
                    }
                }
            }
        }

        // BottomBar di bagian bawah layar
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Menempatkan di bagian bawah layar
            navController = navController
        )
    }

}

@Preview
@Composable
fun AllProkerPreview (){
    val navController = rememberNavController()
    AllProker(navController = navController)
}