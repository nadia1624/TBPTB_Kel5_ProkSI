package com.example.proksi_tbptb.frontend.ChangePassword.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.frontend.ChangePassword.component.PasswordForm
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar

@Composable
fun ChangePasswordScreen(modifier: Modifier = Modifier, navController: NavHostController){
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
            PasswordForm()
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