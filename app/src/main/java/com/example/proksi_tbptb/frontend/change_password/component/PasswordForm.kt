package com.example.proksi_tbptb.frontend.change_password.component

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordForm(
    passwordLama: String,
    passwordBaru: String,
    konfirmasiPassword: String,
    passwordBaruChange: (String) -> Unit,
    passwordLamaChange: (String) -> Unit,
    passwordKonfirmasiChange: (String) -> Unit,
    onClick: () -> Unit
) {

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
            PasswordField(label = "Password", value = passwordLama, onValueChange = {  passwordLamaChange(it) })
            Spacer(modifier = Modifier.height(16.dp))
            Text("New Password",
                fontSize = 18.sp)
            PasswordField(label = "New Password", value = passwordBaru, onValueChange = { passwordBaruChange(it) })
            Spacer(modifier = Modifier.height(12.dp))
            Text("Confirm Password",
                fontSize = 18.sp)
            PasswordField(label = "Confirm Password", value = konfirmasiPassword, onValueChange = { passwordKonfirmasiChange(it) })
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Mengatur Button di sebelah kanan
            ) {
                ButtonChangePassword(
                    onClick = onClick
                )
            }

        }
    }
}

@Preview
@Composable
fun PasswordFormPreview(){
//    PasswordForm()
}