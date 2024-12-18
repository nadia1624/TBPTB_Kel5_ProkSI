package com.example.proksi_tbptb.frontend.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.R

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    pageTitle: String = "Home",
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFFF5E7C6)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo (Kiri) Menggunakan gambar dari drawable
        Image(
            painter = painterResource(id = R.drawable.logoproksi), // Ganti dengan nama file gambar Anda
            contentDescription = "Logo",
            modifier = Modifier
                .size(48.dp) // Anda bisa sesuaikan ukuran logo
                .padding(start = 16.dp)
        )

        // Nama Halaman (Tengah)
        Text(
            text = pageTitle,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF222222),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        // Icon Notifikasi (Kanan)
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifikasi",
                tint = Color(0xFFFF6E1F)
            )
        }
    }
}


@Preview
@Composable
fun TopBarPreview(){
    TopBar()
}