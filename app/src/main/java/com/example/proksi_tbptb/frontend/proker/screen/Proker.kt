package com.example.proksi_tbptb.frontend.proker.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar
import com.example.proksi_tbptb.frontend.proker.LihatProkerFactory
import com.example.proksi_tbptb.frontend.proker.LihatProkerViewModel
import com.example.proksi_tbptb.frontend.proker.component.BoxProker
import com.example.proksi_tbptb.frontend.proker.component.CustomButtonProker

data class ProkerItem(val name: String, val status: String, val id: Int)

@Composable
fun ProkerScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LihatProkerViewModel = viewModel(factory = LihatProkerFactory())
) {
    // Observing live data from the view model
    val prokers by viewModel.prokers.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = true)
    val error by viewModel.error.observeAsState()

    // Fetching token and checking login state
    val context = LocalContext.current
    val userPreferences = UserPreferences()
    val token = remember { mutableStateOf("") }
    val userId = remember { mutableIntStateOf(0) }

    // Fetch token when the screen is first loaded
    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
        userId.value = userPreferences.getUserId(context)
        if (token.value.isNotEmpty() && userId.value != 0) {
            viewModel.fetchProkers(token.value, userId.value)
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
                .padding(bottom = 66.dp)
        ) {
            TopBar(pageTitle = "Proker")

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    modifier = Modifier.padding(end = 100.dp)
                ) {
                    CustomButtonProker(
                        text = "Divisi",
                        onClick = { navController.navigate("proker") },
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

                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    error != null -> {
                        Text(
                            text = error ?: "Unknown error",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(prokers) { proker ->
                                BoxProker(
                                    text = proker.name,
                                    status = proker.status,
                                    onClick = { navController.navigate("detail-proker/${proker.id}?name=${proker.name}&status=${proker.status}")}
                                )
                            }
                        }
                    }
                }
            }
        }

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
    val navController = rememberNavController()
    ProkerScreen(navController = navController)
}
