    package com.example.proksi_tbptb.frontend.detail_proker.screen

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
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
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
    import com.example.proksi_tbptb.data.repository.DetailProkerRepository
    import com.example.proksi_tbptb.frontend.component.BottomBar
    import com.example.proksi_tbptb.frontend.component.TopBar
    import com.example.proksi_tbptb.frontend.detail_proker.DetailProkerFactory
    import com.example.proksi_tbptb.frontend.detail_proker.DetailProkerViewModel
    import com.example.proksi_tbptb.frontend.detail_proker.component.BoxDetailProker
    import com.example.proksi_tbptb.frontend.detail_proker.component.CustomButtonWithCheckIcon
    import com.example.proksi_tbptb.frontend.detail_proker.component.CustomButtonWithIcon
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch

    @Composable
    fun DetailProkerScreen(
        prokerId: Int,
        navController: NavHostController,
        token: String,
        name: String,
        status: String,
        viewModel: DetailProkerViewModel = viewModel(
            factory = DetailProkerFactory(DetailProkerRepository())
        ),
        onBackClick: () -> Unit
    ) {
        val context = LocalContext.current
        val userPreferences = UserPreferences()

        // Observing data from the ViewModel
        val detailProkers by viewModel.detailProkers.observeAsState(initial = emptyList())
        val isLoading by viewModel.isLoading.observeAsState(initial = true)
        val error by viewModel.error.observeAsState()
        val currentStatus by viewModel.detailProkerStatus.observeAsState(initial = "Not Started")
        var isButtonClicked by remember { mutableStateOf(false) }

        // Manage token and userId
        LaunchedEffect(Unit) {
            val userToken = userPreferences.getToken(context).orEmpty()
            Log.d("DetailProkerScreen", "Token: $userToken")

            if (userToken.isNotEmpty()) {
                viewModel.fetchDetailProker(userToken, prokerId)
                Log.d("DetailProkerScreen", "Fetched DetailProkers: ${viewModel.detailProkers.value}")
            } else {
                Log.e("DetailProkerScreen", "Token is empty")
            }
        }

        Scaffold(
            topBar = {
                TopBar(pageTitle = "Detail Proker", onBackClick = onBackClick)
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
                        when {
                            isLoading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
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
                                        // Nama dan Status
                                        Row(
                                            modifier = Modifier.weight(1f),
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

                                            Box(
                                                modifier = Modifier
                                                    .padding(end = 16.dp)
                                                    .background(
                                                        color = when (status) {
                                                            "Done" -> Color(0xFF87B662)
                                                            "In Progress" -> Color(0xFFFFD77A)
                                                            "Not Started" -> Color(0xFFFF6D1F)
                                                            else -> Color(0xFF9E9E9E)
                                                        },
                                                        shape = RoundedCornerShape(16.dp)
                                                    )
                                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = status,
                                                    color = Color.Black,
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }

                                        // Tombol
                                        Row(
                                            horizontalArrangement = Arrangement.End,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (currentStatus != "Done" && !isButtonClicked) {
                                                CustomButtonWithCheckIcon(
                                                    onClick = {
                                                        CoroutineScope(Dispatchers.Main).launch {
                                                            try {
                                                                val userToken = userPreferences.getToken(context).orEmpty()
                                                                Log.d("UpdateStatusScreen", "Token : $userToken")

                                                                if (userToken.isNotEmpty()) {
                                                                    viewModel.updateProkerStatus(context, userToken, prokerId)
                                                                    navController.navigate("proker")
                                                                    isButtonClicked = true
                                                                } else {
                                                                    Log.e("DetailProkerScreen", "Token is empty")
                                                                }
                                                            } catch (e: Exception) {
                                                                Log.e("UpdateStatusScreen", "Error fetching token", e)
                                                            }
                                                        }
                                                    },
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .padding(end = 8.dp),
                                                    buttonColor = Color(0xFFF9B683),
                                                    enabled = currentStatus != "Done"
                                                )
                                            }

                                            if (currentStatus != "Done" && currentStatus != "2") {
                                                CustomButtonWithIcon(
                                                    onClick = {
                                                        navController.navigate("tambah_detail_proker/$prokerId")
                                                    },
                                                    modifier = Modifier.size(40.dp),
                                                    buttonColor = Color(0xFFF9B683),
                                                    enabled = currentStatus != "Done" && currentStatus != "2"
                                                )
                                            }
                                        }
                                    }



                                    // Description Text
                                    Text(
                                        text = "Beberapa rincian program kerja yang dilakukan dalam satu kepengurusan HMSI",
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
                                                onClick = { navController.navigate("detail-proker-2/${detail.idDetailproker}")}
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
