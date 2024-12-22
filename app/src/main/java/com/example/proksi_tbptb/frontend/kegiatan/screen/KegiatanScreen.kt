package com.example.proksi_tbptb.frontend.kegiatan.screen

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
import androidx.navigation.NavHostController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonAbsensi
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar
import com.example.proksi_tbptb.frontend.kegiatan.KegiatanViewModel
import com.example.proksi_tbptb.frontend.kegiatan.component.BoxAbsensi

@Composable
fun KegiatanScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: KegiatanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences()
    val token = remember { mutableStateOf("") }
    val userId = remember { mutableIntStateOf(0) }
    val rekapAbsensi by viewModel.rekapAbsensi.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
        userId.intValue = userPreferences.getUserId(context)
        if (token.value.isNotEmpty() && userId.intValue != 0) {
            viewModel.fetchRiwayatAbsensi(token.value, userId.intValue)
        }
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
            TopBar(pageTitle = "Absensi")

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
                        buttonColor = Color(0xFFF5E7C6),
                        contentColor = Color.Black
                    )
                    CustomButtonAbsensi(
                        text = "Global",
                        onClick = {
                            navController.navigate("all-kegiatan")
                        },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF9B683),
                        contentColor = Color.Black
                    )
                    Spacer(modifier = Modifier.width(138.dp))
                }

                // Menampilkan loading atau error
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Terjadi kesalahan",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (rekapAbsensi == null) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    // Menampilkan data riwayat absensi
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(22.dp)
                    ) {
                        items(rekapAbsensi?.data?.size ?: 0) { index ->
                            val absensi = rekapAbsensi?.data?.get(index)
                            BoxAbsensi(
                                text = absensi?.kegiatan?.namaKegiatan.orEmpty(),
                                status = absensi?.statusAbsensi ?: 0,
                                jamKegiatan = absensi?.kegiatan?.jamKegiatan.orEmpty(),  // Passing jamKegiatan
                                tanggal = absensi?.kegiatan?.tanggalKegiatan.orEmpty(),
                                onClick = { navController.navigate("tambah-kegiatan/${absensi?.idKegiatan}") }
                            )
                        }
                    }
                }
            }
        }

        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Menempatkan di bagian bawah layar
            navController = navController
        )
    }
}