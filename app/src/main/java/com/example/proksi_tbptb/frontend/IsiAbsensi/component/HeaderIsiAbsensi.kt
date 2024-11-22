package com.example.proksi_tbptb.frontend.IsiAbsensi.component

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
fun HeaderIsiAbsensi (titleText: String){
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
fun HeaderIsiAbsensiPreview (){
    HeaderIsiAbsensi(titleText = "Piket 3 - Januari")
}