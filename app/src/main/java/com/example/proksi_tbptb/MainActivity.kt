package com.example.proksi_tbptb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.frontend.AbsensiTerkirim.screen.AbsensiTerkirimScreen
import com.example.proksi_tbptb.frontend.IsiAbsensi.screen.IsiAbsensiScreen
import com.example.proksi_tbptb.frontend.Proker.screen.ProkerScreen
import com.example.proksi_tbptb.frontend.absensi.screen.AbsensiScreen
import com.example.proksi_tbptb.frontend.allproker.screen.AllProker
import com.example.proksi_tbptb.frontend.home.HomePage
import com.example.proksi_tbptb.frontend.isikegiatan.IsiKegiatanViewModel
import com.example.proksi_tbptb.frontend.isikegiatan.screen.IsiKegiatanScreen
import com.example.proksi_tbptb.frontend.kegiatan.screen.KegiatanScreen
import com.example.proksi_tbptb.frontend.login.LoginPage
import com.example.proksi_tbptb.ui.theme.ProkSI_TBPTBTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferences = UserPreferences()
        val apiService = ApiConfig.api // Pastikan ApiService dibuat dengan benar

        CoroutineScope(Dispatchers.Main).launch {
            val isLoggedIn = userPreferences.getLoginState(this@MainActivity)
            val token = userPreferences.getToken(this@MainActivity)

            setContent {
                ProkSI_TBPTBTheme {
                    val navController = rememberNavController()
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = if (isLoggedIn) "home" else "login",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("login") { LoginPage(navController = navController) }
                            composable("home") { HomePage(navController = navController) }
                            composable("absensi") {
                                AbsensiScreen(navController = navController, context = androidx.compose.ui.platform.LocalContext.current)
                            }
                            composable("proker") { ProkerScreen(navController = navController) }
                            composable("tambah-absensi") { IsiAbsensiScreen(navController = navController) }
                            composable("all-proker") { AllProker(navController = navController) }
                            composable("all-kegiatan") { KegiatanScreen(navController = navController) }
                            composable("tambah-kegiatan/{id_kegiatan}") { backStackEntry ->
                                val idKegiatan = backStackEntry.arguments?.getString("id_kegiatan")?.toIntOrNull()
                                Log.d("IsiKegiatanScreen", "idKegiatan: $idKegiatan")
                                val viewModel: IsiKegiatanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

                                LaunchedEffect(idKegiatan) {
                                    idKegiatan?.let {
                                        viewModel.fetchDetailKegiatan(token = token.toString(), idKegiatan = it)
                                    }
                                }
                                IsiKegiatanScreen(
                                    navController = navController,
                                )
                            }
                            composable("absensi-terkirim/{id_rekapan}") { backStackEntry ->
                                val idRekapan = backStackEntry.arguments?.getString("id_rekapan")?.toIntOrNull()
                                val token = backStackEntry.arguments?.getString("token") ?: ""
                                println("Id Rekapan ${idRekapan}")
                                if (idRekapan != null) {
                                    AbsensiTerkirimScreen(
                                        navController = navController,
                                        idRekapan = idRekapan,
                                        token = token
                                    )
                                } else {
                                    // Handle invalid or missing idRekapan
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
