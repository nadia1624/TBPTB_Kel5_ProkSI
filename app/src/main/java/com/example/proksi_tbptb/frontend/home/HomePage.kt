package com.example.proksi_tbptb.frontend.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar


@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val context = LocalContext.current // Mendapatkan Context dari LocalContext
    Box(
        modifier = Modifier.fillMaxSize() // Gunakan Box untuk tata letak layar penuh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp) // Beri ruang untuk BottomBar
        ) {
            TopBar(pageTitle = "Home", onBackClick = {})
            Text(text = "Home Page",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // BottomBar ditempatkan di bagian bawah dengan align
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Menempatkan BottomBar di bawah
            navController = navController
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewHomePage() {
//    val navController = rememberNavController() // Dummy untuk preview
//    HomePage(navController = navController)
//}
