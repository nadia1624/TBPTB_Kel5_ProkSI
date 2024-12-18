package com.example.proksi_tbptb.frontend.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.frontend.component.BottomBar
import com.example.proksi_tbptb.frontend.component.TopBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val profileData = viewModel.profileState.value // Amati state di sini
    val userPreferences = remember { UserPreferences() }
    val token = remember { mutableStateOf("") }
    val userId = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
        userId.intValue = userPreferences.getUserId(context) // Ganti dengan ID user yang sesuai
        viewModel.getProfile(token.value, userId.intValue)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF4E2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
        ) {
            TopBar(pageTitle = "Profile")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCFA1)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.BottomEnd // Menempatkan ikon di sudut kanan bawah
                    ) {
                        if (profileData?.gambar != null) {
                            AsyncImage(
                                model = "http://10.0.2.2:3000/profil/profile/image",
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.elemen5),
                                contentDescription = "Default Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        }

                        // Tombol Edit
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit), // Ganti dengan ikon edit Anda
                            contentDescription = "Edit Profile Picture",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .padding(4.dp)
                                .clickable {
                                    // TODO: Tambahkan logika untuk mengedit foto profil
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${profileData?.namaDepan ?: "[Nama Depan]"} ${profileData?.namaBelakang ?: "[Nama Belakang]"}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = profileData?.nim ?: "[NIM]",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "${profileData?.divisi?.namaDivisi ?: "[Divisi]"} - ${profileData?.jabatan ?: "[Jabatan]"}",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileField(label = "Email", value = profileData?.email ?: "[Email]")
                    Spacer(modifier = Modifier.height(8.dp))
                    ProfileField(label = "Jadwal Piket", value = profileData?.jadwal ?: "[Jadwal]")

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = {
                            viewModel.logout(context)
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }) {
                            Text("sign out", color = Color.Black)
                        }
                        TextButton(onClick = { /* TODO: Change password action */ }) {
                            Text("change password", color = Color.Black)
                        }
                    }
                }
            }
        }
        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Digunakan dengan `Box`
            navController = navController
        )
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(
            value,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF4E2), shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            textAlign = TextAlign.Start
        )
    }
}
