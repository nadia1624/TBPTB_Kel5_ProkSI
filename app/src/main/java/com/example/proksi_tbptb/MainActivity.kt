package com.example.proksi_tbptb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import com.example.proksi_tbptb.frontend.absensi.screen.AbsensiScreen
import com.example.proksi_tbptb.frontend.absensi_terkirim.screen.AbsensiTerkirimScreen
import com.example.proksi_tbptb.frontend.all_proker.screen.AllProker
import com.example.proksi_tbptb.frontend.change_password.screen.ChangePasswordScreen
import com.example.proksi_tbptb.frontend.detail_proker.screen.DetailProkerScreen
import com.example.proksi_tbptb.frontend.detail_proker_2.DetailProker2Screen
import com.example.proksi_tbptb.frontend.home.HomePage
import com.example.proksi_tbptb.frontend.isi_absensi.screen.IsiAbsensiScreen
import com.example.proksi_tbptb.frontend.isi_kegiatan.IsiKegiatanViewModel
import com.example.proksi_tbptb.frontend.isi_kegiatan.screen.IsiKegiatanScreen
import com.example.proksi_tbptb.frontend.kegiatan.screen.KegiatanScreen
import com.example.proksi_tbptb.frontend.login.LoginPage
import com.example.proksi_tbptb.frontend.profile.ProfileScreen
import com.example.proksi_tbptb.frontend.proker.screen.ProkerScreen
import com.example.proksi_tbptb.frontend.tambah_detail_proker.TambahDetailProkerScreen
import com.example.proksi_tbptb.ui.theme.ProkSI_TBPTBTheme
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var pendingNavigation: String? = null
    private val userPreferences = UserPreferences()
    private val apiService = ApiConfig.api // Ensure ApiService is created correctly

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAndSendToken()
        checkFcmToken()
        intent?.let { checkIntentForNavigation(it) }

        // Using lifecycleScope to launch coroutines tied to the lifecycle of this Activity
        lifecycleScope.launch {
            val isLoggedIn = userPreferences.getLoginState(this@MainActivity)
            val token = userPreferences.getToken(this@MainActivity)

            setContent {
                ProkSI_TBPTBTheme {
                    val navController = rememberNavController()
                    LaunchedEffect(pendingNavigation) {
                        pendingNavigation?.let { destination ->
                            navController.navigate(destination) {
                                popUpTo("home") { inclusive = true }
                            }
                            pendingNavigation = null // Reset setelah digunakan
                        }
                    }
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = if (isLoggedIn) "home" else "login",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("login") { LoginPage(navController = navController) }
                            composable("home") { HomePage(navController = navController) }
                            composable("absensi") {
                                AbsensiScreen(
                                    navController = navController,
                                    context = LocalContext.current,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("proker") {
                                ProkerScreen(
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("tambah-absensi") {
                                IsiAbsensiScreen(
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("all-proker") {
                                AllProker(
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("all-kegiatan") {
                                KegiatanScreen(
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("tambah-kegiatan/{id_kegiatan}") { backStackEntry ->
                                val idKegiatan = backStackEntry.arguments?.getString("id_kegiatan")?.toIntOrNull()
                                Log.d("IsiKegiatanScreen", "idKegiatan: $idKegiatan")
                                val viewModel: IsiKegiatanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

                                LaunchedEffect(idKegiatan) {
                                    idKegiatan?.let {
                                        viewModel.fetchDetailKegiatan(token = token.toString(), idKegiatan = it)
                                    }
                                }
                                IsiKegiatanScreen(navController = navController, onBackClick = { navController.popBackStack() })
                            }
                            composable(
                                route = "detail-proker/{id}?name={name}&status={status}",
                                arguments = listOf(
                                    navArgument("id") { type = NavType.IntType },
                                    navArgument("name") { type = NavType.StringType },
                                    navArgument("status") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val prokerId = backStackEntry.arguments?.getInt("id") ?: 0
                                val token = backStackEntry.arguments?.getString("token") ?: ""
                                val name = backStackEntry.arguments?.getString("name").orEmpty()
                                val status = backStackEntry.arguments?.getString("status").orEmpty()


                                DetailProkerScreen(
                                    prokerId= prokerId,
                                    navController = navController,
                                    token = token,
                                    name = name,
                                    status = status,
                                    onBackClick = { navController.popBackStack() } // Navigasi kembali

                                )
                            }
                            composable(
                                route = "tambah_detail_proker/{prokerId}",
                                arguments = listOf(
                                    navArgument("prokerId") { type = NavType.StringType }  // Changed to match the type expected by API
                                )
                            ) { backStackEntry ->
                                val prokerId = backStackEntry.arguments?.getString("prokerId") ?: ""  // Changed to getString
                                TambahDetailProkerScreen(
                                    navController = navController,
                                    prokerId = prokerId,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("absensi-terkirim/{id_rekapan}") { backStackEntry ->
                                val idRekapan = backStackEntry.arguments?.getString("id_rekapan")?.toIntOrNull()
                                val token = backStackEntry.arguments?.getString("token") ?: ""
                                println("Id Rekapan $idRekapan")
                                if (idRekapan != null) {
                                    AbsensiTerkirimScreen(
                                        navController = navController,
                                        idRekapan = idRekapan,
                                        token = token,
                                        onBackClick = { navController.popBackStack() }
                                    )
                                } else {
                                    Toast.makeText(this@MainActivity, "Id Rekapan tidak ditemukan", Toast.LENGTH_SHORT).show()
                                }
                            }
                            composable("profile") { ProfileScreen(navController = navController, onBackClick = { navController.popBackStack() }) }
                            composable(
                                route = "detail-proker-2/{idDetailProker}",
                                arguments = listOf(
                                    navArgument("idDetailProker") {
                                        type = NavType.IntType
                                        nullable = false
                                    }
                                )
                            ) { backStackEntry ->
                                val idDetailProker = backStackEntry.arguments?.getInt("idDetailProker")
                                val token = backStackEntry.arguments?.getString("token") ?: ""
                                Log.d("DetailProkerNavigation", "ID Detail Proker: $idDetailProker")

                                if (idDetailProker != null) {
                                    DetailProker2Screen(
                                        navController = navController,
                                        idDetailProker = idDetailProker,
                                        onBackClick = { navController.popBackStack() },
                                        token = token
                                    )
                                } else {
                                    LaunchedEffect(Unit) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "ID Detail Proker tidak ditemukan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                            composable("change-password") { ChangePasswordScreen(navController = navController) }
                        }
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun checkFcmToken() {
        lifecycleScope.launch {
            val token = userPreferences.getFcmToken(applicationContext)
            if (token == null) {
                // Request new token jika belum ada
                FirebaseMessaging.getInstance().token
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val newToken = task.result
                            GlobalScope.launch {
                                userPreferences.saveFcmToken(applicationContext, newToken)
                            }
                        }
                    }
            }
        }
    }

    private fun sendTokenToServer(token: String) {
        val apiService = ApiConfig.api
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("FirebaseMain", "Sending token to server: $token")
                val response = apiService.registerToken(ApiService.TokenRequest(token))
                if (response.isSuccessful) {
                    Log.d("FirebaseMain", "Token registered successfully")
                } else {
                    Log.e("FirebaseMain", "Failed to register token: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("FirebaseMain", "Error registering token", e)
            }
        }
    }

    // Panggil fungsi ini saat aplikasi pertama kali dibuka
    private fun getAndSendToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FirebaseMain", "Retrieved token: $token")
                    sendTokenToServer(token)
                } else {
                    Log.e("FirebaseMain", "Failed to get FCM token", task.exception)
                }
            }
    }

    private fun checkIntentForNavigation(intent: Intent) {
        if (intent.hasExtra("navigation_destination")) {
            pendingNavigation = intent.getStringExtra("navigation_destination")
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        checkIntentForNavigation(intent)
    }
}