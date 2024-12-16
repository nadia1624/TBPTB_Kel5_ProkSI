package com.example.proksi_tbptb.frontend.isikegiatan

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.IsiAbsenKegiatanResponse
import com.example.proksi_tbptb.data.remote.response.IsiKegiatanResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IsiKegiatanViewModel : ViewModel() {

    private val _detailKegiatan = MutableLiveData<IsiKegiatanResponse?>()
    val detailKegiatan: LiveData<IsiKegiatanResponse?> = _detailKegiatan

    private val _uploadResponse = MutableLiveData<IsiAbsenKegiatanResponse?>()
    val uploadResponse: LiveData<IsiAbsenKegiatanResponse?> = _uploadResponse

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDetailKegiatan(token: String, idKegiatan: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.api.absensiKegiatan("Bearer $token", idKegiatan)
                Log.d("IsiKegiatanViewModelFetch", "idKegiatan: $idKegiatan")
                if (response.isSuccessful) {
                    _detailKegiatan.value = response.body()
                    Log.d("IsiKegiatanViewModel", "Berhasil memuat data: ${response.body()}")
                } else {
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                    Log.e("IsiKegiatanViewModel", "Gagal memuat data: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.localizedMessage}"
                Log.e("IsiKegiatanViewModel", "Terjadi kesalahan: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadAbsensiKegiatan(token: String, idKegiatan: Int, userId: Int, imageUri: Uri, context: Context) {
        _isLoading.value = true
        Log.e("IsiKegiatanViewModel", "Pre-upload idKegiatan: $idKegiatan")
        viewModelScope.launch {
            try {
                if (idKegiatan == 0) {
                    _errorMessage.value = "ID Kegiatan tidak valid"
                    return@launch
                }

                val mimeType = context.contentResolver.getType(imageUri)
                if (!isImageMimeTypeAllowed(mimeType)) {
                    _errorMessage.value = "Format file tidak didukung. Gunakan JPG, JPEG, atau PNG"
                    return@launch
                }

                val file = uriToFile(imageUri, context)
                Log.d("IsiKegiatanViewModel", "File size: ${file.length()} bytes")
                Log.d("IsiKegiatanViewModel", "File path: ${file.absolutePath}")

                // Create RequestBody instance from file
                val requestFile = file.asRequestBody((mimeType ?: "image/jpeg").toMediaType())
                val imagePart = MultipartBody.Part.createFormData("gambar", file.name, requestFile)

                // Create userId RequestBody
                val userIdBody = userId.toString().toRequestBody("text/plain".toMediaType())

                val response = ApiConfig.api.isiAbsensiKegiatan(
                    "Bearer $token",
                    idKegiatan,
                    userIdBody,
                    imagePart
                )

                if (response.isSuccessful) {
                    _uploadResponse.value = response.body()
                    Log.d("IsiKegiatanViewModel", "Berhasil upload: ${response.body()}")
                } else {
                    _errorMessage.value = "Gagal upload: ${response.message()}"
                    val errorBody = response.errorBody()?.string()
                    Log.e("IsiKegiatanViewModel", "Gagal upload: ${response.message()}")
                    Log.e("IsiKegiatanViewModel", "Gagal upload dengan kode: ${response.code()}")
                    Log.e("IsiKegiatanViewModel", "Error body: $errorBody")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
                Log.e("IsiKegiatanViewModel", "Error: ${e.message}")
                Log.e("IsiKegiatanViewModel", "Error detail: ${e.stackTraceToString()}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk mengecek MIME type
    private fun isImageMimeTypeAllowed(mimeType: String?): Boolean {
        val allowedTypes = listOf(
            "image/jpeg",
            "image/jpg",
            "image/png"
        )
        return mimeType in allowedTypes
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
        Log.d("IsiKegiatanViewModel", "File MIME type: $mimeType")

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(myFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("ABSENSI_${timeStamp}_", ".jpg", storageDir)
    }
}