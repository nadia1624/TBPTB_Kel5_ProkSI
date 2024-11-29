package com.example.proksi_tbptb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.frontend.DetailProker.screen.DetailProkerScreen
import com.example.proksi_tbptb.frontend.login.LoginPage
import com.example.proksi_tbptb.ui.theme.ProkSI_TBPTBTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProkSI_TBPTBTheme {
                val navController = rememberNavController()

                // Navigation host
                NavHost(navController = navController, startDestination = "loginPage") {
                    composable("loginPage") {
                        LoginPage(navController = navController)
                    }
                    composable("detailProkerScreen") {
                        DetailProkerScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun DetailProkerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Detail Proker Screen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        // Tambahkan komponen lain untuk menampilkan detail proker sesuai kebutuhan
    }
}



