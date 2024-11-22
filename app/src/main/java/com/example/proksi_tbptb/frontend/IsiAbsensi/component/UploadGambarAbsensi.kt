package com.example.proksi_tbptb.frontend.IsiAbsensi.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.R

@Composable
fun UploadGambarAbsensi (){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = "",
            onValueChange = { /* Update value */ },
            placeholder = { Text("Bukti foto") },
            trailingIcon = {
                IconButton(onClick = { /* Upload action */ }) {
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
    UploadGambarAbsensi()
}