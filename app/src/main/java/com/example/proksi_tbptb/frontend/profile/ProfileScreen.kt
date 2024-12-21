package com.example.proksi_tbptb.frontend.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
    val profileData = viewModel.profileState.value
    val userPreferences = remember { UserPreferences() }
    val token = remember { mutableStateOf("") }
    val userId = remember { mutableIntStateOf(0) }

    val imageTimestamp = remember { mutableLongStateOf(System.currentTimeMillis()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(context, it)
            imageTimestamp.longValue = System.currentTimeMillis()
        }
    }


    LaunchedEffect(Unit) {
        token.value = userPreferences.getToken(context).orEmpty()
        userId.intValue = userPreferences.getUserId(context)
        viewModel.getProfile(token.value, userId.intValue)
    }

    Box(
        modifier = Modifier.fillMaxSize() // Gunakan Box untuk tata letak layar penuh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp) // Beri ruang untuk BottomBar
        ) {
            TopBar(pageTitle = "Profile")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCFA1)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFF4E2)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (profileData?.gambar != null) {
                            AsyncImage(
                                model = coil.request.ImageRequest.Builder(LocalContext.current)
                                    .data("http://10.0.2.2:3000/profil/profile/image?t=${imageTimestamp.longValue}")
                                    .addHeader("Authorization", "Bearer ${token.value}")
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(100.dp)
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
                    }

                    Text(
                        text = "Edit Profile",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {
                                launcher.launch("image/*")
                            },
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline
                    )

                    if (viewModel.isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${profileData?.namaDepan ?: "[Nama Pengurus]"} ${profileData?.namaBelakang ?: ""}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = profileData?.nim ?: "[NIM Pengurus]",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "${profileData?.divisi?.namaDivisi ?: "[Divisi Pengurus]"} - ${profileData?.jabatan ?: "[Jabatan]"}",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ProfileField(
                        label = "Email",
                        value = profileData?.email ?: "Email A",
                        backgroundColor = Color(0xFFFFF4E2)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileField(
                        label = "Jadwal Piket",
                        value = profileData?.jadwal ?: "Senin",
                        backgroundColor = Color(0xFFFFF4E2)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "sign out",
                            modifier = Modifier
                                .clickable {
                                    viewModel.logout(context)
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black
                        )
                        Text(
                            text = "change password",
                            modifier = Modifier
                                .clickable { navController.navigate("change-password") },
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        BottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Menempatkan BottomBar di bawah
            navController = navController
        )
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    backgroundColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = value,
                fontSize = 16.sp
            )
        }
    }
}
