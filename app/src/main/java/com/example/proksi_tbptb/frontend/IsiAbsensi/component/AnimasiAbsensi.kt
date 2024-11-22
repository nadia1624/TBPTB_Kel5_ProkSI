package com.example.proksi_tbptb.frontend.IsiAbsensi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.R

@Composable
fun AnimasiAbsensi (){
    Image(
        painter = painterResource(id = R.drawable.element4),
        contentDescription = "Illustration",
        modifier = Modifier.size(400.dp)
    )
}

@Preview (showBackground = true)
@Composable
fun AnimasiAbsensiPreview (){
    AnimasiAbsensi()
}