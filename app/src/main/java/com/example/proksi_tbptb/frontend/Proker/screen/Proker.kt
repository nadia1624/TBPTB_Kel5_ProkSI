package com.example.proksi_tbptb.frontend.Proker.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.Proker.component.BoxProker
import com.example.proksi_tbptb.frontend.Proker.component.CustomButtonProker

// Data class untuk mewakili item Proker
data class ProkerItem(val name: String, val status: String)

@Composable
fun ProkerScreen(
    data: List<ProkerItem> = listOf(), // Data diterima sebagai parameter
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
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
            TopBar(pageTitle = "Proker")

            // Konten utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), // Mengatur elemen-elemen agar dimulai dari kiri
                    modifier = Modifier.padding(end = 100.dp)) {
                    CustomButtonProker(
                        text = "Divisi",
                        onClick = { println("Divisi diklik") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF9B683),
                        contentColor = Color.Black
                    )
                    CustomButtonProker(
                        text = "All",
                        onClick = { navController.navigate("all-proker") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF5E7C6),
                        contentColor = Color.Black
                    )
                }

                // LazyColumn untuk menampilkan data Proker
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data) { item ->
                        BoxProker(
                            text = item.name, // Nama Proker
                            status = item.status, // Status Proker
                            onClick = { println("Item ${item.name} diklik") }
                        )
                    }
                }
            }
        }

        // BottomBar di bagian bawah layar
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            navController = navController
        )
    }
}

@Preview
@Composable
fun ProkerScreenPreview() {
    val dummyData = remember {
        listOf(
            ProkerItem("Seminar Hasil", "Done"),
            ProkerItem("Kaderisasi", "In Progress"),
            ProkerItem("Mahasiswa Prestasi", "Not Started")
        )
    }
    val navController = rememberNavController()
    ProkerScreen(data = dummyData, navController = navController)
}
