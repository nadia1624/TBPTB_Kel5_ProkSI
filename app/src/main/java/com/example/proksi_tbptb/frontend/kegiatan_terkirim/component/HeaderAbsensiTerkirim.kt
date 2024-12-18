package com.example.proksi_tbptb.frontend.kegiatan_terkirim.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HeaderAbsensiTerkirim (titleText : String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewHeaderAbsensiTerkirim (){
    HeaderAbsensiTerkirim(titleText = "Minggu Ke -1")
}