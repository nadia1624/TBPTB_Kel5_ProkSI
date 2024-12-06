package com.example.proksi_tbptb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.frontend.IsiAbsensi.screen.IsiAbsensiScreen
import com.example.proksi_tbptb.frontend.login.LoginPage
import com.example.proksi_tbptb.frontend.Proker.screen.ProkerScreen
import com.example.proksi_tbptb.frontend.absensi.screen.AbsensiScreen
import com.example.proksi_tbptb.frontend.allproker.screen.AllProker
import com.example.proksi_tbptb.frontend.home.HomePage
import com.example.proksi_tbptb.ui.theme.ProkSI_TBPTBTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProkSI_TBPTBTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login", // Halaman awal
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
                    }
                }
            }
        }
    }
}
