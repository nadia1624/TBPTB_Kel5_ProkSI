package com.example.proksi_tbptb.frontend.IsiAbsensi.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.proksi_tbptb.R

@Composable
fun UploadGambarAbsensi (
    onClick: () -> Unit,
    imageUri: Uri? = null
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = "",
            onValueChange = { /* Update value */ },
            placeholder =  {
                if (imageUri != null) {
                    // Tampilkan preview gambar di placeholder
                    androidx.compose.foundation.Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Preview Gambar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFAF3E1))
                            .padding(6.dp)
                    )
                } else {
                    // Tampilkan placeholder teks biasa jika gambar belum dipilih
                    Text("Bukti foto")
                }
            },
            trailingIcon = {
                IconButton(onClick = onClick ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_upload), // Ganti resource ikon
                        contentDescription = "Upload"
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun UploadGambarAbsensiPreview (){
//    UploadGambarAbsensi()
}