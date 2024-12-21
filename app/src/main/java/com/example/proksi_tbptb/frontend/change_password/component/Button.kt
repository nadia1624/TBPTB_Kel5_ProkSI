package com.example.proksi_tbptb.frontend.change_password.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonChangePassword(onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF6E1F), // Warna latar belakang tombol
            contentColor = Color.White // Warna teks
        ),
        shape = RoundedCornerShape(8.dp), // Mengurangi lengkungan tombol
        modifier = Modifier
            .height(48.dp) // Menentukan tinggi tombol
            .padding(horizontal = 16.dp) // Memberikan padding horizontal
            .shadow(0.dp) // Menghilangkan bayangan tombol
    ) {
        Text(
            text = "Selesai",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold, // Memberikan ketebalan pada teks
            letterSpacing = 1.25.sp // Sedikit spasi antar huruf agar lebih rapi
        )
    }
}



@Preview
@Composable
fun ButtonChangePasswordPreview(){
    ButtonChangePassword()
}