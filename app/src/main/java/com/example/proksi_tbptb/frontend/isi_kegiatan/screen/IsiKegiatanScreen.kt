package com.example.proksi_tbptb.frontend.isi_kegiatan.screen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.component.TopBar
import com.example.proksi_tbptb.frontend.isi_kegiatan.IsiKegiatanViewModel

@Composable
fun IsiKegiatanScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onBackClick: () -> Unit,
    viewModel: IsiKegiatanViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val context = LocalContext.current
    val detailKegiatan by viewModel.detailKegiatan.observeAsState()
    val uploadResponse by viewModel.uploadResponse.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    val userPreferences = remember { UserPreferences() }
    val token = remember { mutableStateOf("") }
    val userId = remember { mutableIntStateOf(0) }
    val idKegiatan = navController.currentBackStackEntry?.arguments?.getString("id_kegiatan")

    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
        userId.intValue = userPreferences.getUserId(context)
        Log.d("IsiKegiatanScreen", "idKegiatanScreenIniKan: $idKegiatan")

        if (token.value.isNotEmpty() && idKegiatan != null) {
            viewModel.fetchDetailKegiatan(token.value, idKegiatan.toInt())
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFFAF3E1))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBar(pageTitle = "Detail Kegiatan", onBackClick = onBackClick)

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Terjadi kesalahan",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                when (detailKegiatan?.statusAbsensi) {
                    0 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Ups! Kamu sudah tidak bisa mengambil absensi",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    1 -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Upload Foto Absensi",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            if (imageUri != null) {
                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.LightGray)
                                        .clickable { launcher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Photo",
                                        tint = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    imageUri?.let {
                                        if (idKegiatan != null) {
                                            viewModel.uploadAbsensiKegiatan(
                                                token.value,
                                                idKegiatan.toInt(),
                                                userId.intValue,
                                                it,
                                                context
                                            )
                                        }
                                        Log.d(
                                            "UPLOAD",
                                            "idKegiatan yang diterima di uploadAbsensiKegiatan: $idKegiatan"
                                        )
                                    }
                                    navController.navigate("all-kegiatan") {
                                        popUpTo("tambah-kegiatan/{id_kegiatan}") {
                                            inclusive = true
                                        }
                                    }
                                    Toast.makeText(
                                        context,
                                        "Absensi berhasil diisi!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                enabled = imageUri != null
                            ) {
                                Text("Upload Foto")
                            }
                        }
                    }

                    2 -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Absensi Berhasil!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            // Check if gambar is available in the detailKegiatan object
                            if (detailKegiatan?.gambar != null) {
                                AsyncImage(
                                    model = "http://10.0.2.2:3000/uploads/${detailKegiatan?.gambar}",
                                    contentDescription = "Uploaded Image",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(
                                    text = "Tidak ada gambar absensi.",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
