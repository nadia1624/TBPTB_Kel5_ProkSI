package com.example.proksi_tbptb.frontend.absensi.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.absensi.component.BoxAbsensi
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonAbsensi
import com.example.proksi_tbptb.frontend.absensi.component.CustomButtonWithIcon

@Composable
fun AbsensiScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF3E1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp) // Memberikan ruang untuk BottomBar
        ) {
            // TopBar di bagian atas
            TopBar(pageTitle = "Absensi")

            // Konten utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CustomButtonAbsensi(
                        text = "Piket",
                        onClick = { println("Piket diklik") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF9B683),
                        contentColor = Color.Black
                    )
                    CustomButtonAbsensi(
                        text = "Global",
                        onClick = { println("Global diklik") },
                        modifier = Modifier.weight(1f),
                        buttonColor = Color(0xFFF5E7C6),
                        contentColor = Color.Black
                    )
                    Spacer(modifier = Modifier.width(64.dp))
                    CustomButtonWithIcon(
                        onClick = { println("Tambah diklik") },
                        modifier = Modifier,
                        buttonColor = Color(0xFFF9B683)
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(2.dp) // Jarak antar item
                ) {
                    val item = ApiConfig.api.lihatAbsensi()
                    items(10) { index ->  // 5 adalah contoh jumlah item yang ingin ditampilkan
                        BoxAbsensi(
                            text = "Absensi ${index + 1}", // Menampilkan "Absensi 1", "Absensi 2", dll.
                            painter = painterResource(id = R.drawable.ceklis), // Gambar yang ditampilkan
                            onClick = { println("Absensi ${index + 1} diklik") } // Fungsi ketika diklik
                        )
                    }
                }
            }
        }

        // BottomBar di bagian bawah layar
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Menempatkan di bagian bawah layar
        )
    }
}

@Preview
@Composable
fun AbsensiScreenPreview() {
    AbsensiScreen()
}
