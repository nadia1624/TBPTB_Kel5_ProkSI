package com.example.proksi_tbptb.frontend.change_password.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.data.User
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.ChangePassword.ChangePasswordViewModel
import com.example.proksi_tbptb.frontend.ChangePassword.component.PasswordForm
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.change_password.component.PasswordForm
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar

@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangePasswordViewModel = viewModel(),
    navController: NavHostController
){
    val userPreferences = remember { UserPreferences() }
    var token by remember { mutableStateOf("") }
    val context = LocalContext.current

    var passwordLama by remember { mutableStateOf("") }
    var passwordBaru by remember { mutableStateOf("") }
    var konfirmasiPassword by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        token = userPreferences.getToken(context).orEmpty()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF3E1)) // Background untuk seluruh screen
    )
    Column {
        TopBar(pageTitle = "Change Password")
        Column(
            modifier = modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PasswordForm(
                passwordLama = passwordLama,
                passwordBaru = passwordBaru,
                konfirmasiPassword = konfirmasiPassword,
                passwordLamaChange = { passwordLama = it },
                passwordBaruChange = { passwordBaru = it },
                passwordKonfirmasiChange = { konfirmasiPassword = it },
                onClick = {
                    viewModel.changePassword(
                        token = token,
                        passwordLama = passwordLama,
                        passwordBaru = passwordBaru,
                        konfirmasiPassword = konfirmasiPassword,
                        onSuccess = {
                            Toast.makeText(context, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                            navController.navigate("profile")
                        },
                        onError = {
                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.element4), // Ganti dengan ilustrasi Anda
                contentDescription = "Illustration",
                modifier = Modifier.size(400.dp)
            )

        }
        BottomBar(modifier = Modifier.fillMaxWidth(),navController = navController)
    }


}

@Preview
@Composable
fun ChangePasswordPreview(){
    val navController = rememberNavController()
    ChangePasswordScreen(navController = navController)
}