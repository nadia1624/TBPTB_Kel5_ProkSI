package com.example.proksi_tbptb.frontend.DetailProker.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.DetailProker.DetailProkerFactory
import com.example.proksi_tbptb.frontend.DetailProker.DetailProkerViewModel
import com.example.proksi_tbptb.frontend.DetailProker.component.BoxDetailProker
import com.example.proksi_tbptb.frontend.DetailProker.component.CustomButtonWithCheckIcon
import com.example.proksi_tbptb.frontend.DetailProker.component.CustomButtonWithIcon

@Composable
fun DetailProkerScreen(
    prokerId: Int,
    navController: NavHostController,
    token: String,
    name: String,
    status: String,
    viewModel: DetailProkerViewModel = viewModel(factory = DetailProkerFactory())
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences()

    // Observing data from the ViewModel
    val detailProkers by viewModel.detailProkers.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = true)
    val error by viewModel.error.observeAsState()

    // Manage token and userId
    LaunchedEffect(Unit) {
        val userToken = userPreferences.getToken(context).orEmpty()
        Log.d("DetailProkertoken", "DetailProkers: ${userToken}")

        if (userToken.isNotEmpty()) {
            viewModel.fetchDetailProker(userToken, prokerId)
            Log.d("DetailProkerScreen", "DetailProkers: ${viewModel.detailProkers.value}")
        } else {
            Log.e("DetailProkerScreen", "Token is empty")
        }
    }

    Scaffold(
        topBar = {
            TopBar(pageTitle = "Detail Proker")
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAF3E1))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 66.dp)
                ) {
                    // Handle loading, error, or content display
                    when {
                        isLoading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(50.dp))
                            }
                        }

                        error != null -> {
                            Text(
                                text = error ?: "Unknown error occurred",
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        else -> {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                            ) {
                                // Header Row
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 23.sp
                                        ),
                                        color = Color.Black,
                                        modifier = Modifier.weight(1f)
                                    )

                                    // Status Box
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = if (status == "Active") Color(0xFF4CAF50) else Color.Gray,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = status,
                                            color = Color.White
                                        )
                                    }
                                }

                                // Buttons Row
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(), // Membuat Row mengisi lebar layar
                                    horizontalArrangement = Arrangement.End // Menempatkan elemen-elemen di sebelah kanan
                                ) {
                                    CustomButtonWithCheckIcon(
                                        onClick = { /* Handle check action */ },
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(end = 8.dp), // Memberikan ruang antar tombol
                                        buttonColor = Color(0xFFF9B683)
                                    )

                                    CustomButtonWithIcon(
                                        onClick = { /* Handle add action */ },
                                        modifier = Modifier.size(40.dp),
                                        buttonColor = Color(0xFFF9B683)
                                    )
                                }

                                // Description Text
                                Text(
                                    text = "Description of the proker and its details will be displayed here.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )

                                // LazyColumn for detail Proker items
                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(detailProkers) { detail ->
                                        BoxDetailProker(
                                            text = detail.judulDetailProker ?: "",
                                            date = viewModel.formatDate(detail.tanggal),
                                            onClick = { /* Handle detail item click */ }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
//@Preview
//@Composable
//fun DetailProkerScreenPreview() {
//    val navController = rememberNavController()
//
//    // Use the same factory
//    val viewModel: DetailProkerViewModel = viewModel(factory = DetailProkerFactory())
//
//    DetailProkerScreen(
//        prokerId = 1, // Use a valid proker ID from your database
//        navController = navController
//    )
//}

