package com.example.proksi_tbptb.frontend.ChangePassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordForm() {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9B683)) // Atur warna background Card di sini

    ) {
        Column(
            modifier = Modifier.padding(16.dp),

            ) {
            Text("Password Lama",
                fontSize = 18.sp)
            PasswordField(label = "Password", value = oldPassword, onValueChange = { oldPassword = it })
            Spacer(modifier = Modifier.height(16.dp))
            Text("New Password",
                fontSize = 18.sp)
            PasswordField(label = "New Password", value = newPassword, onValueChange = { newPassword = it })
            Spacer(modifier = Modifier.height(12.dp))
            Text("Confirm Password",
                fontSize = 18.sp)
            PasswordField(label = "Confirm Password", value = confirmPassword, onValueChange = { confirmPassword = it })
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Mengatur Button di sebelah kanan
            ) {
                ButtonChangePassword()
            }

        }
    }
}

@Preview
@Composable
fun PasswordFormPreview(){
    PasswordForm()
}