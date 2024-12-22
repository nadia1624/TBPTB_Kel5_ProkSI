package com.example.proksi_tbptb.frontend.tambah_detail_proker

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar
import java.util.Calendar

@Composable
fun TambahDetailProkerScreen(
    navController: NavHostController,
    prokerId: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences()

    // Observe ViewModel state
    val viewModel: TambahDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TambahDetailViewModel(context) as T
            }
        }
    )

    val isLoading by viewModel.isLoading.collectAsState()

    // Local state management
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var gambar by remember { mutableStateOf<Uri?>(null) }

    // Token and UserId (fetching from preferences)
    val tokenValue = remember { mutableStateOf("") }
    val userId = remember { mutableIntStateOf(0) }

    // Just validate token and userId on launch
    LaunchedEffect(Unit) {
        tokenValue.value = userPreferences.getToken(context) ?: ""
        userId.intValue = userPreferences.getUserId(context)
        Log.d("TambahDetailProkerScreen", "Proker ID: $prokerId")
        Log.d("TambahDetailProkerScreen", "Token: ${tokenValue.value}")
        Log.d("TambahDetailProkerScreen", "User ID: ${userId.intValue}")
    }

    // Image Picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> gambar = uri }

    Scaffold(
        topBar = { TopBar(pageTitle = "Tambah Proker", onBackClick = onBackClick) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAF3E1))
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Detail Proker", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))

                    // Judul Proker
                    Text("Judul", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = judul,
                        onValueChange = { judul = it },
                        label = { Text("Masukkan Judul Proker") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFFDF0D5),
                            unfocusedBorderColor = Color(0xFFFDF0D5)
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
                            .padding(bottom = 16.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)),
                        readOnly = true,
                        trailingIcon = {
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
                        }
                    )

                    // Bukti Foto dengan Placeholder
                    Text("Bukti Foto", style = MaterialTheme.typography.titleMedium)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6))
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .clickable { imagePickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (gambar == null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Filled.PhotoLibrary,
                                    contentDescription = "Pilih Foto",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(40.dp)
                                )
                                Text("Klik untuk memilih foto", color = Color.Gray)
                            }
                        } else {
                            AsyncImage(
                                model = gambar,
                                contentDescription = "Preview Foto",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // Tombol Selesai
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                if (judul.isNotEmpty() && tanggal.isNotEmpty()) {
                                    viewModel.addProkerDetail(
                                        token = tokenValue.value,
                                        idProker = prokerId,
                                        judulDetailProker = judul,
                                        tanggal = tanggal,
                                        gambar = gambar
                                    )
                                    navController.navigate("proker")
                                    Toast.makeText(context, "Detail Proker berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Harap lengkapi judul dan tanggal!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            enabled = !isLoading,
                            modifier = Modifier
                                .height(48.dp)
                                .padding(horizontal = 5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF6F00),
                                disabledContainerColor = Color(0xFFFF6F00).copy(alpha = 0.5f)
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            } else {
                                Text("Selesai", color = Color.White)
                            }
                        }
                    }
                }

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