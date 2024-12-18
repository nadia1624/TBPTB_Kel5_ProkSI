package com.example.proksi_tbptb.frontend.TambahDetailProker

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.Component.BottomBar
import com.example.proksi_tbptb.frontend.Component.TopBar
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahDetailProkerScreen(
    navController: NavHostController,
    prokerId: String,
    token: String,
) {
    val context = LocalContext.current
    val viewModel: TambahDetailViewModel = viewModel()
    val userPreferences = UserPreferences()

    // State Management
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Observe ViewModel states
    val addProkerState by viewModel.addProkerState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Image Picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Just validate token on launch
    LaunchedEffect(Unit) {
        val userToken = userPreferences.getToken(context).orEmpty()
        Log.d("TambahDetailProkertoken", "Token: ${userToken}")

        if (userToken.isEmpty()) {
            Log.e("TambahDetailProkerScreen", "Token is empty")
            // You could add navigation back or show an error message here
        }
    }

    Scaffold(
        topBar = { TopBar(pageTitle = "Tambah Proker") },
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
                    Text("Detail Proker",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp))

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

                    // Bukti Foto
                    Text("Bukti Foto", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = imageUri?.lastPathSegment ?: "",
                        onValueChange = { },
                        label = { Text("Ambil Foto dari Galeri") },
                        placeholder = { Text("Pilih foto dari galeri") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5E7C6), RoundedCornerShape(8.dp)),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                imagePickerLauncher.launch("image/*")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.PhotoLibrary,
                                    contentDescription = "Ambil Foto dari Galeri"
                                )
                            }
                        }
                    )

                    // Preview Image jika ada
                    imageUri?.let { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "Preview Foto",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
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
                                        token = token,
                                        idProker = prokerId,
                                        judulDetailProker = judul,
                                        tanggal = tanggal,
                                        imageUri = imageUri
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Harap lengkapi judul dan tanggal!",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
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
//@Preview(showBackground = true)
//@Composable
//fun TambahDetailProkerPreview() {
//    val navController = rememberNavController()
//    TambahDetailProkerScreen(navController = navController, prokerId = 1)
//}