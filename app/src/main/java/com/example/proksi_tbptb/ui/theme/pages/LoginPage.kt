package com.example.proksi_tbptb.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proksi_tbptb.R

@Composable
fun LoginPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF0D5))
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logoproksi),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(top = 5.dp)
                )
                Spacer(modifier = Modifier.height(0.dp))

                Image(
                    painter = painterResource(R.drawable.logoproksi),
                    contentDescription = "Mascot Image",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Welcome!",
                    fontSize = 20.sp,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(24.dp))

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("EMAIL") },
                    placeholder = { Text("Enter your email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

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

                Button(
                    onClick = { /* Handle login logic here */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00)),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginPagePreview (){
    LoginPage()
}
