package com.example.proksi_tbptb.frontend.AbsensiTerkirim.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.AbsensiTerkirimViewModel
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.AnimasiAbsensiTercatat
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.BuktiFoto
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.HeaderAbsensiTerkirim
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.component.statusCustom
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar

@Composable
fun AbsensiTerkirimScreen(
    modifier: Modifier = Modifier,
    idRekapan: Int,
    navController: NavHostController,
    token: String, // Tambahkan token sebagai parameter
    viewModel: AbsensiTerkirimViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userPreferences = UserPreferences()
    val context = LocalContext.current
    val token = remember { mutableStateOf("") }
    // Panggil fetchDetailAbsensi saat layar dimuat
    LaunchedEffect(Unit) {
        val userToken = userPreferences.getToken(context).orEmpty()
        if (userToken.isNotEmpty()) {
            token.value = userToken
            viewModel.fetchDetailAbsensi(userToken, idRekapan)
        } else {
            Log.e("AbsensiTerkirimScreen", "Token is empty")
        }
    }


    val state by viewModel.absensiDetailState.collectAsState()

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
            TopBar(pageTitle = "Absensi")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    when (val currentState = state) {
                        is AbsensiTerkirimViewModel.AbsensiDetailState.Success -> {
                            val detail = currentState.detail
                            HeaderAbsensiTerkirim(titleText = "Minggu Ke-${detail.idRekapan ?: "-"}")
                            statusCustom(
                                text = if (detail.status == 1) "Done" else "Pending",
                                color = if (detail.status == 1) Color(0xFF87B662) else Color(0xFFFFA726)
                            )
                        }
                        is AbsensiTerkirimViewModel.AbsensiDetailState.Loading -> {
                            HeaderAbsensiTerkirim(titleText = "Loading...")
                            statusCustom(text = "Loading", color = Color.Gray)
                        }
                        is AbsensiTerkirimViewModel.AbsensiDetailState.Error -> {
                            HeaderAbsensiTerkirim(titleText = "Error")
                            statusCustom(text = currentState.message, color = Color.Red)
                        }
                    }
                }

                if (state is AbsensiTerkirimViewModel.AbsensiDetailState.Success) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BuktiFoto()
                        Spacer(modifier = Modifier.height(16.dp))
                        AnimasiAbsensiTercatat()
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
