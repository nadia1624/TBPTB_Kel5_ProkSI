package com.example.proksi_tbptb.frontend.absensi.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CustomButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.Blue  // Parameter untuk warna latar belakang
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor  // Warna latar belakang tombol
        ),
        shape = RoundedCornerShape(12.dp),  // Memberikan sudut membulat pada tombol
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add, // Ikon bawaan Compose
            contentDescription = "Tambah",
            tint = Color.Black, // Warna ikon
            modifier = Modifier.size(24.dp) // Ukuran ikon
        )
    }
}

@Preview
@Composable
fun CustomButtonIconPreview() {
    CustomButtonWithIcon(
        onClick = { println("Tambah diklik") },
        buttonColor = Color(0xFFF9B683)  // Menentukan warna untuk preview
    )
}