package com.example.proksi_tbptb.frontend.kegiatan_terkirim.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.R

@Composable
fun BuktiFoto (){
        Column {
            Text(text = "Bukti Foto")
            Box(
                modifier = Modifier
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
            ){
                Image(
                    painter = painterResource(id = R.drawable.element4),  // Ganti dengan gambar Anda
                    contentDescription = "Upload Icon",
                    modifier = Modifier.size(300.dp) // Ukuran gambar
                )
            }

        }
    }

@Preview
@Composable
fun PreviewBuktiFoto(){
    BuktiFoto()
}