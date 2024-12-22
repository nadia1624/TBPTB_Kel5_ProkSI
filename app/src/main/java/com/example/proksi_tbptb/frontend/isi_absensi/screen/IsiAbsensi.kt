package com.example.proksi_tbptb.frontend.isi_absensi.screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar
import com.example.proksi_tbptb.frontend.isi_absensi.IsiAbsensiViewModel
import com.example.proksi_tbptb.frontend.isi_absensi.component.AnimasiAbsensi
import com.example.proksi_tbptb.frontend.isi_absensi.component.ButtonKirimAbsensi
import com.example.proksi_tbptb.frontend.isi_absensi.component.HeaderIsiAbsensi
import com.example.proksi_tbptb.frontend.isi_absensi.component.UploadGambarAbsensi

@Composable
fun IsiAbsensiScreen (
    modifier: Modifier = Modifier,
    viewModel: IsiAbsensiViewModel = viewModel(),
    context: Context = LocalContext.current,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val userPreferences = remember { UserPreferences() }
    val token = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Box(
        modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFFAF3E1))
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp)
        ){
            TopBar(pageTitle = "Absensi", onBackClick = onBackClick)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                HeaderIsiAbsensi(titleText = "Minggu Ke-1")
                UploadGambarAbsensi(
                    onClick = {
                    imagePickerLauncher.launch("image/*")
                },
                    imageUri = imageUri
                )
                ButtonKirimAbsensi(
                    text = "Kirim",
                    onClick = {
                        viewModel.isiAbsensi(
                            context = context,
                            token = token.value,
                            imageUri = imageUri,
                            onSuccess = {
                                Toast.makeText(context, "Absensi berhasil dikirim", Toast.LENGTH_SHORT).show()
                                navController.navigate("absensi")
                            },
                            onError = {errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    buttonColor = Color(0xFFFF6E1F),  // Warna latar belakang
                    contentColor = Color.White      // Warna teks
                )
                AnimasiAbsensi()
            }
        }
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Menempatkan di bagian bawah layar
            navController = navController
        )

    }
}

//@Preview
//@Composable
//fun IsiAbsensiPreview(){
//    val navController = rememberNavController()
//    IsiAbsensiScreen(navController = navController)
//}