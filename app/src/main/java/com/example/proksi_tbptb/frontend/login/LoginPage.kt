package com.example.proksi_tbptb.frontend.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.ui.theme.BackgroundColor
import com.example.proksi_tbptb.ui.theme.PrimaryButtonColor
import com.example.proksi_tbptb.ui.theme.TextColor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun LoginPage(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    // State untuk email dan password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            if (loginViewModel.isLoading.value == true) {
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
            loginViewModel.errorMessage.value?.let {
                Text(
                    text = it,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            // Menampilkan pesan sukses jika login berhasil
            loginViewModel.loginResponse.value?.let {
                Toast.makeText(LocalContext.current, "Login Successful", Toast.LENGTH_SHORT).show()
                navController.navigate("detailProkerScreen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    val navController = rememberNavController()
    LoginPage(navController = navController)}
