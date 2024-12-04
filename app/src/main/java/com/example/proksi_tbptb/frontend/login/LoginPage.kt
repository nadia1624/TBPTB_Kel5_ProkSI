package com.example.proksi_tbptb.frontend.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.ui.theme.BackgroundColor
import com.example.proksi_tbptb.ui.theme.PrimaryButtonColor
import com.example.proksi_tbptb.ui.theme.TextColor

@Composable
fun LoginPage(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    // State untuk email dan password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Mengambil state dari ViewModel
    val isLoading by loginViewModel.isLoading.observeAsState(false)
    val errorMessage by loginViewModel.errorMessage.observeAsState()
    val loginResponse by loginViewModel.loginResponse.observeAsState()

    val context = LocalContext.current

    // Kolom utama untuk layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(28.dp)
    ) {
        // Logo bagian atas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.logoproksi),
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Gambar logo perusahaan
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Company Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            // Gambar maskot
            Image(
                painter = painterResource(R.drawable.proksi),
                contentDescription = "Mascot Image",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Judul selamat datang
            Text(
                text = "Welcome!",
                fontSize = 40.sp,
                color = TextColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Menampilkan input email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("EMAIL") },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan input password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("PASSWORD") },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Menampilkan CircularProgressIndicator saat loading
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Tombol Login
                Button(
                    onClick = {
                        loginViewModel.loginUser(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(PrimaryButtonColor),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Login",
                        fontSize = 16.sp
                    )
                }
            }

            // Menampilkan pesan error jika login gagal
            errorMessage?.let {
                Text(
                    text = it,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            // Menampilkan pesan sukses jika login berhasil
            loginResponse?.let {
                LaunchedEffect(it) {
                    // Menampilkan Toast setelah login berhasil
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                    // Simpan status login menggunakan SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("ProksiPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                    // Navigasi ke HomeScreen
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewLoginPage() {
    val navController = rememberNavController()
    LoginPage(navController = navController)
}