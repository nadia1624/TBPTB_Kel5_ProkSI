package com.example.proksi_tbptb.frontend.profile

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.response.DataUser
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel : ViewModel() {
    private val userPreferences = UserPreferences()
    private val apiService = ApiConfig.api

    val profileState = mutableStateOf<DataUser?>(null) // Tambahkan state untuk menyimpan data profil

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    fun logout(context: Context) {
        viewModelScope.launch {
            userPreferences.logout(context)
        }
    }

    fun getProfile(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                // Use .value to extract the actual token string
                val response = apiService.profile("Bearer $token", userId)
                if (response.isSuccessful) {
                    val profileResponse = response.body()
                    profileState.value = profileResponse?.data
                    Log.d("ProfileViewModel", "Profile data: ${profileResponse?.data}")
                } else {
                    Log.e("ProfileViewModel", "Profile API call failed: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching profile", e)
            }
        }
    }

    fun uploadImage(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val token = userPreferences.getToken(context).orEmpty()
                val file = uriToFile(context, imageUri)

                val requestImageFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imageMultipart = MultipartBody.Part.createFormData(
                    "gambar",
                    file.name,
                    requestImageFile
                )

                val response = apiService.uploadImage("Bearer $token", imageMultipart)

                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfileViewModel", "Error response body: $errorBody")
                    Log.e("ProfileViewModel", "Response code: ${response.code()}")
                    Log.e("ProfileViewModel", "Response message: ${response.message()}")
                } else {
                    val userId = userPreferences.getUserId(context)
                    getProfile(token, userId)
                    Log.d("ProfileViewModel", "Upload successful")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error uploading image", e)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Utility function to convert Uri to File
    private fun uriToFile(context: Context, uri: Uri): File {
        val contentResolver = context.contentResolver
        val myFile = createTempFile(context)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(myFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return myFile
    }

    private fun createTempFile(context: Context): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        )
    }
}