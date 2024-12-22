package com.example.proksi_tbptb.frontend.detail_proker_2

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.proksi_tbptb.BuildConfig
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.response.DetailProker2Data
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar
import com.example.proksi_tbptb.frontend.detail_proker.DetailProker2ViewModel

@Composable
fun DetailProker2Screen(
    viewModel: DetailProker2ViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = DetailProker2ViewModel.Factory()
    ),
    idDetailProker: Int,
    token: String,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences()

    val detailProkerState by viewModel.detailProker.observeAsState(initial = null)
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val errorMessage by viewModel.error.observeAsState(initial = null)

    // Improved token management and error handling
    LaunchedEffect(Unit) {
        try {
            val storedToken = userPreferences.getToken(context).orEmpty()
            Log.d("DetailProkerScreen", "Token: $storedToken")

            if (storedToken.isNotEmpty()) {
                viewModel.fetchDetailProker2(idDetailProker, storedToken)
            } else {
                viewModel.setError("Token tidak ditemukan")
                Log.e("DetailProkerScreen", "Token is empty")
            }
        } catch (e: Exception) {
            viewModel.setError("Error mengambil token: ${e.message}")
            Log.e("DetailProkerScreen", "Error getting token", e)
        }
    }

    Scaffold(
        topBar = { TopBar(pageTitle = "Detail Proker", onBackClick = onBackClick) },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF3E1))
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    LoadingIndicator()
                }
                detailProkerState != null -> {
                    DetailProkerContent(detailProker = detailProkerState!!)
                }
                else -> {
                    EmptyState()
                }
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Data tidak tersedia",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
private fun DetailProkerContent(detailProker: DetailProker2Data) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        ProkerDetailSection("Judul:", detailProker.judulDetailProker)
        ProkerDetailSection("Tanggal:", detailProker.tanggal)

        Text(
            text = "Bukti Foto:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val baseUrl = BuildConfig.BASE_URL
        val gambar = baseUrl + (detailProker.gambar)

        ProkerImage(gambar = gambar )

    }
}

@Composable
private fun ProkerImage(gambar: String?) {
    Box(modifier = Modifier
        .border(2.dp,Color.Black, RoundedCornerShape(4.dp))){
        Image(
            painter = rememberAsyncImagePainter(model = gambar),
            contentDescription = "gambar proker",
            modifier = Modifier.size(300.dp)
        )
    }
}

@Composable
fun ProkerDetailSection(title: String, content: String?) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = content ?: "Tidak tersedia", // Default text if null
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}
