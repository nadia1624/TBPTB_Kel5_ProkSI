package com.example.proksi_tbptb.frontend.isi_absensi

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

//@HiltViewModel
class IsiAbsensiViewModel : ViewModel() {
    private val apiService = ApiConfig.api

    fun isiAbsensi(
        token: String,
        context: Context,
        imageUri: Uri? = null,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (!token.isNullOrEmpty()) {
                try {
                    val imagePart = imageUri?.let {
                        val contentResolver = context.contentResolver
                        val type = contentResolver.getType(it) ?: "image/*"
                        val inputStream = contentResolver.openInputStream(it)
                        val bytes = inputStream?.readBytes() ?: byteArrayOf()
                        inputStream?.close()

                        val requestBody = RequestBody.create(type.toMediaTypeOrNull(), bytes)
                        MultipartBody.Part.createFormData("gambar", "image.jpg", requestBody)
                    }

                    val response = apiService.createAbsensi(
                        token = "Bearer $token",
                        image = imagePart
                    )

                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Bukan jadwal absen piket mu!")
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    onError("Terjadi kesalahan: ${e.message}")
                }
            } else {
                onError("Token tidak ditemukan")
            }
        }
    }
}