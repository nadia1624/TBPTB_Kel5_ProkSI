package com.example.proksi_tbptb.frontend.all_proker.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proksi_tbptb.BuildConfig
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.all_proker.component.CustomButtonAllProker
import com.example.proksi_tbptb.frontend.all_proker.component.ProkerCard
import com.example.proksi_tbptb.frontend.allproker.AllProkerViewModel
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar

@Composable
fun AllProker(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AllProkerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit
) {
    val allProker by viewModel.allProker.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    val userPreferences = remember { UserPreferences() }
    var token by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        token = userPreferences.getToken(context).orEmpty()
        viewModel.fetchAllDetailProker(token = token)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF3E1))
    ) {
        when {
            isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                    )

            }
            isError -> {
                    Text(
                        text = errorMessage ?: "Terjadi kesalahan",
                        color = Color.Red
                    )

            }
            else -> {
                // Menampilkan konten jika data berhasil dimuat
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 66.dp) // Memberikan ruang untuk BottomBar
                ) {
                    // TopBar di bagian atas
                    TopBar(pageTitle = "All Proker", onBackClick = onBackClick)

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
                                onClick = { navController.navigate("proker") },
                                modifier = Modifier.weight(1f),
                                buttonColor = Color(0xFFF5E7C6),
                                contentColor = Color.Black
                            )
                            CustomButtonAllProker(
                                text = "All",
                                onClick = { navController.navigate("all-proker") },
                                modifier = Modifier.weight(1f),
                                buttonColor = Color(0xFFF9B683),
                                contentColor = Color.Black
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(allProker) { detailProker ->
                                val baseUrl = BuildConfig.BASE_URL
                                val gambarUrl = baseUrl + (detailProker.gambar)
                                Log.d("AllProkerScreen", "Gambar URL: $gambarUrl")
                                ProkerCard(
                                    title = detailProker.judulDetailProker.toString(),
                                    divisi = detailProker.proker.divisi.namaDivisi.toString(),
                                    gambar = gambarUrl,
                                    onClick = { println("Proker diklik") }
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
    }
}