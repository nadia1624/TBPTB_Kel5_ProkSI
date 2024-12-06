package com.example.proksi_tbptb.frontend.DetailProker.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import com.example.proksi_tbptb.frontend.DetailProker.component.BoxDetailProker
import com.example.proksi_tbptb.frontend.DetailProker.component.CustomButtonWithIcon
import com.example.proksi_tbptb.frontend.DetailProker.component.CustomButtonWithCheckIcon
import com.example.proksi_tbptb.frontend.DetailProker.component.getStatusByName

@Composable
fun DetailProkerScreen(
    prokerDetails: List<Pair<String, String>>, // Parameter untuk daftar nama dan tanggal proker
    status: String,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val statusObj = getStatusByName(status)

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
            TopBar(pageTitle = "Proker")

            // Konten utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {

                // Row untuk Kotak Status, Tombol Ceklis dan Tombol Tambah
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Memberikan jarak antar elemen
                    verticalAlignment = Alignment.CenterVertically // Menyusun elemen di tengah secara vertikal
                ) {
                    Text(
                        text = "Detail Proker",
                        modifier = Modifier.padding(bottom = 8.dp), // Memberikan jarak antara teks dan LazyColumn
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        ),
                        color = Color.Black
                    )

                    // Kotak Status
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(
                                color = statusObj.color,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = statusObj.name, // Menampilkan nama status
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    // Tombol Ceklis dengan ukuran lebih kecil
                    CustomButtonWithCheckIcon(
                        onClick = { println("Ceklis diklik") },
                        modifier = Modifier
                            .size(40.dp) // Menentukan ukuran tombol
                            .weight(0.5f), // Menyebar secara merata
                        buttonColor = Color(0xFFF9B683)
                    )

                    // Tombol Tambah dengan ukuran lebih kecil
                    CustomButtonWithIcon(
                        onClick = { println("Tambah diklik") },
                        modifier = Modifier
                            .size(40.dp) // Menentukan ukuran tombol
                            .weight(0.5f), // Menyebar secara merata
                        buttonColor = Color(0xFFF9B683)
                    )

                }
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget ex ornare, finibus nibh finibus, finibus metus. Proin quis lacus accumsan, tristique dolor dictum, lacinia sem. ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 3.dp) // Memberikan jarak antara teks judul dan deskripsi
                )

                // LazyColumn untuk menampilkan detail proker
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(prokerDetails) { (name, date) ->
                        BoxDetailProker(
                            text = name,
                            date = date, // Menampilkan tanggal
                            onClick = { println("Item $name dengan tanggal $date diklik") }
                        )
                    }
                }
            }
        }

        // BottomBar di bagian bawah layar
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Menempatkan di bagian bawah layar
            navController = navController
        )
    }
}

@Preview
@Composable
fun DetailProkerScreenPreview() {
    val navController = rememberNavController()
    DetailProkerScreen(
        prokerDetails = listOf(
            "Seminar Hasil 1" to "27 Nov 2024",
            "Seminar Hasil 2" to "30 Nov 2024",
            "Seminar Hasil 3" to "5 Des 2024"
        ),
        status = "Done",
        navController = navController
    )
}
