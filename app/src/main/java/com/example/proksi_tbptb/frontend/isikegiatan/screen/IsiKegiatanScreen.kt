package com.example.proksi_tbptb.frontend.isikegiatan.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.isikegiatan.IsiKegiatanViewModel

@Composable
fun IsiKegiatanScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: IsiKegiatanViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val context = LocalContext.current
    val detailKegiatan by viewModel.detailKegiatan.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    // Ambil token dan ID kegiatan
    val userPreferences = UserPreferences()
    val token = remember { mutableStateOf("") }
    val idKegiatan = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
        if (token.value.isNotEmpty()) {
            Log.d("IsiKegiatanScreen", "Token: ${token.value}")
        } else {
            Log.e("IsiKegiatanScreen", "Token is empty")
        }
        idKegiatan.value = navController.previousBackStackEntry?.arguments?.getInt("idKegiatan") ?: 0
        if (idKegiatan.value != 0) {
            Log.d("IsiKegiatanScreen", "ID Kegiatan: ${idKegiatan.value}")
        } else {
            Log.e("IsiKegiatanScreen", "ID Kegiatan is 0")
        }
        if (token.value.isNotEmpty() && idKegiatan.value != 0) {
            viewModel.fetchDetailKegiatan(token.value, idKegiatan.value)
        }
    }

    // UI Layout
    Box(modifier = modifier.fillMaxSize().background(Color(0xFFFAF3E1))) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            TopBar(pageTitle = "Detail Kegiatan")

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Terjadi kesalahan",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (detailKegiatan != null) {
                val statusAbsensi = detailKegiatan?.statusAbsensi ?: 0

                when (statusAbsensi) {
                    0 -> {
                        // Jika statusAbsensi = 0
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Ups! Kamu sudah tidak bisa mengambil absensi",
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 20.sp
                            )
                        }
                    }

                    1 -> {
                        // Jika statusAbsensi = 1
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Upload Foto",
                                color = Color.Black,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            // Placeholder foto
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .background(Color.Gray)
                            ) {
                                Text(
                                    text = "Foto Placeholder",
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            // Tombol Upload
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                // Tambahkan logika upload
                                Log.d("Upload", "Upload Foto Button Clicked")
                            }) {
                                Text("Upload Foto")
                            }
                        }
                    }

                    2 -> {
                        // Jika statusAbsensi = 2
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Foto yang sudah diupload",
                                color = Color.Black,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            // Tampilkan foto yang sudah diupload
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .background(Color.Gray)
                            ) {
                                // Ganti dengan Image jika URL tersedia
                                Text(
                                    text = "Foto Uploaded",
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
