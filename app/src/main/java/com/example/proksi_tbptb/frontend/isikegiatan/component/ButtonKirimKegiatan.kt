package com.example.proksi_tbptb.frontend.isikegiatan.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonKirimKegiatan (
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.Blue,  // Parameter untuk warna latar belakang
    contentColor: Color = Color.White // Parameter untuk warna teks
){
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
    Button(
        onClick = onClick,
        modifier = modifier.align(Alignment.CenterEnd),
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

}

@Preview
@Composable
fun ButtonKirimKegiatanPreview (){
    ButtonKirimKegiatan(
        text = "Kirim",
        onClick = { println("Kirim Absen diklik") },
        buttonColor = Color(0xFFFF6E1F),  // Warna latar belakang
        contentColor = Color.White      // Warna teks
    )
}