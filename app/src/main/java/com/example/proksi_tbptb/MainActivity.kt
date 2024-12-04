package com.example.proksi_tbptb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.frontend.home.HomePage
import com.example.proksi_tbptb.frontend.login.LoginPage
import com.example.proksi_tbptb.ui.theme.ProkSI_TBPTBTheme

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
                        composable("home") { HomePage() }
                    }
                }
            }
        }
    }
}

