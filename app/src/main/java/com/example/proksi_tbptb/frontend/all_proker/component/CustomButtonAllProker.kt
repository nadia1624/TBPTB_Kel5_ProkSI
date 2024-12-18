package com.example.proksi_tbptb.frontend.all_proker.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomButtonAllProker(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.Blue,  // Parameter untuk warna latar belakang
    contentColor: Color = Color.White // Parameter untuk warna teks
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,  // Warna latar belakang tombol
            contentColor = contentColor    // Warna teks pada tombol
        ),
        shape = RoundedCornerShape(12.dp)  // Memberikan sudut membulat pada tombol
    ) {
        Text(
            text = text// Menambahkan padding pada teks, jika perlu
        )
    }
}

@Preview
@Composable
fun CustomButtonAllProkerPreview(){
    CustomButtonAllProker(
        text = "Proker",
        onClick = { println("Proker diklik") },
        buttonColor = Color(0xFFF9B683),  // Warna latar belakang
        contentColor = Color.Black       // Warna teks
    )
}