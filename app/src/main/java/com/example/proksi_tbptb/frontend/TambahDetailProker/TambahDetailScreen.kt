package com.example.proksi_tbptb.frontend.TambahDetailProker

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import java.util.Calendar

class TambahDetailProkerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TambahDetailProkerScreen(navController = rememberNavController())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahDetailProkerScreen(navController: NavHostController) {
    val context = LocalContext.current
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var buktiFoto by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(pageTitle = "Tambah Proker") },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAF3E1)) // Background di luar kolom
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp), // Padding dalam kolom
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Teks di atas "Judul Proker"
                    Text(
                        text = "Detail Proker",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Judul Proker
                    Text("Judul", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = judul,
                        onValueChange = { judul = it },
                        label = { Text("Masukkan Judul Proker") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp) // Jarak antar komponen
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFFDF0D5), // Border saat fokus dengan warna 0xFFFDF0D5
                            unfocusedBorderColor = Color(0xFFFDF0D5) // Border saat tidak fokus dengan warna 0xFFFDF0D5
                        )
                    )

                    // Tanggal Proker
                    Text("Tanggal", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = tanggal,
                        onValueChange = { tanggal = it },
                        label = { Text("Pilih Tanggal") },
                        placeholder = { Text("yyyy-mm-dd") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp) // Jarak antar komponen
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)),
                        readOnly = true, // Membuat input hanya bisa diisi dengan DatePicker
                        trailingIcon = { // Ikon di sebelah kanan
                            IconButton(onClick = {
                                val calendar = Calendar.getInstance()
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)
                                DatePickerDialog(
                                    context,
                                    { _, selectedYear, selectedMonth, selectedDay ->
                                        tanggal = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                                    },
                                    year, month, day
                                ).show()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarToday,
                                    contentDescription = "Pilih Tanggal"
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF5E7C6),
                            unfocusedBorderColor = Color(0xFFF5E7C6)
                        )
                    )

                    // Bukti Foto
                    Text("Bukti Foto", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = buktiFoto,
                        onValueChange = { buktiFoto = it },
                        label = { Text("Ambil Foto dari Galeri") },
                        placeholder = { Text("Masukkan URL atau pilih dari galeri") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp) // Jarak antar komponen
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)),
                        readOnly = true, // Membuat input hanya bisa diisi dengan memilih foto
                        trailingIcon = { // Ikon di sebelah kanan
                            IconButton(onClick = {
                                Toast.makeText(context, "Fitur galeri akan ditambahkan!", Toast.LENGTH_SHORT).show()
                                // Logika memilih gambar dari galeri dapat ditambahkan di sini.
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.PhotoLibrary,
                                    contentDescription = "Ambil Foto dari Galeri"
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF5E7C6),
                            unfocusedBorderColor = Color(0xFFF5E7C6)
                        )
                    )

                    // Tombol Selesai di kanan
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), // Pastikan Row mengambil lebar penuh
                        horizontalArrangement = Arrangement.End // Memposisikan tombol di sebelah kanan
                    ) {
                        Button(
                            onClick = {
                                if (judul.isNotEmpty() && tanggal.isNotEmpty() && buktiFoto.isNotEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Proker berhasil ditambahkan!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Harap lengkapi semua field!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .padding(horizontal = 5.dp), // Padding kiri dan kanan untuk tombol
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF6F00) // Warna tombol
                            )
                        ) {
                            Text(
                                "Selesai",
                                color = Color.White // Teks tombol berwarna putih
                            )
                        }
                    }
                }

                // Menambahkan kembali BottomBar di bagian bawah
                BottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    navController = navController
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TambahDetailProkerPreview() {
    val navController = rememberNavController()
    TambahDetailProkerScreen(navController = navController)
}
