package com.example.proksi_tbptb.frontend.absensi.screen

import android.content.Context
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.absensi.component.BoxAbsensi
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonAbsensi
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonWithIcon
import com.example.proksi_tbptb.frontend.absensi.viewmodel.AbsensiViewModel

@Composable
fun AbsensiScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    context: Context,
    absensiViewModel: AbsensiViewModel = viewModel() // Menyuntikkan ViewModel
) {
    // Mengambil data absensi dan status loading/error dari ViewModel
    val absensiList = absensiViewModel.absensiList.collectAsState().value
    val isLoading = absensiViewModel.isLoading.collectAsState().value
    val errorMessage = absensiViewModel.errorMessage.collectAsState().value

    // Fetch absensi saat layar pertama kali ditampilkan
    LaunchedEffect(true) {
        absensiViewModel.fetchAbsensi(context)
    }

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
            TopBar(pageTitle = "Absensi")

            // Konten utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CustomButtonAbsensi(
                        text = "Piket",
                        onClick = { navController.navigate("absensi") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF9B683),
                        contentColor = Color.Black
                    )
                    CustomButtonAbsensi(
                        text = "Global",
                        onClick = {
                            navController.navigate("all-kegiatan")
                        },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF5E7C6),
                        contentColor = Color.Black
                    )
                    Spacer(modifier = Modifier.width(64.dp))
                    CustomButtonWithIcon(
                        onClick = { navController.navigate("tambah-absensi") },
                        modifier = Modifier,
                        buttonColor = Color(0xFFF9B683)
                    )
                }

                // Menampilkan status loading atau error jika ada
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (errorMessage != null) {
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // Menampilkan daftar absensi
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(2.dp) // Jarak antar item
                ) {
                    // Cek apakah absensiList kosong atau tidak
                    if (absensiList.isNotEmpty()) {
                        items(absensiList.size) { index ->
                            val absensiItem = absensiList[index]
                            // Tentukan gambar berdasarkan status absensi
                            val status = absensiItem.status // Asumsikan absensiItem memiliki properti `status`
                            val imageRes = when (status) {
                                0 -> R.drawable.pending
                                1 -> R.drawable.ceklis
                                2 -> R.drawable.decline
                                else -> R.drawable.pending
                            }
                            BoxAbsensi(
                                text = absensiItem.idRekapan?.toString()
                                    ?: "Absensi ${index + 1}", // ID Rekapan atau fallback
                                painter = painterResource(id = imageRes), // Gambar yang ditampilkan (bisa diubah dengan gambar lain)
                                onClick = { println("Absensi ${absensiItem.idRekapan} diklik") } // Fungsi ketika diklik
                            )
                        }
                    } else {
                        // Jika list kosong, tampilkan pesan
                        item {
                            Text(
                                text = "Tidak ada data absensi",
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
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
fun AbsensiScreenPreview() {
    val navController = rememberNavController()
    AbsensiScreen(navController = navController, context = androidx.compose.ui.platform.LocalContext.current)
}
