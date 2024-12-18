package com.example.proksi_tbptb.frontend.absensi_terkirim.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun statusCustom(
    text: String,
    color: Color
){
    Box (
        modifier = Modifier
            .padding(8.dp)
            .clip(
                RoundedCornerShape(16.dp))
            .background(color)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ){
        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun statusCustomPreview(){
    statusCustom(text = "Done", color = Color(0xFF87B662))
}