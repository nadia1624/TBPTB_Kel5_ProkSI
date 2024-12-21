package com.example.proksi_tbptb.frontend.tambah_detail_proker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.TambahDetailResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

class TambahDetailViewModel (private val context: Context) : ViewModel() {

    private val CHANNEL_ID = "detail_proker_channel"
    private val NOTIFICATION_ID = 1

    init {
        createNotificationChannel()
    }

    private val _addProkerState = MutableStateFlow<Result<TambahDetailResponse>?>(null)
    val addProkerState: StateFlow<Result<TambahDetailResponse>?> = _addProkerState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiService = ApiConfig.api

    fun addProkerDetail(
        token: String,
        idProker: String,
        judulDetailProker: String,
        tanggal: String,
        gambar: Uri?
    ) {
        viewModelScope.launch {
            try {
                val result = addProkerDetailToApi(token, idProker, judulDetailProker, tanggal, gambar)
                if (result.isSuccess) {
                    val notifResponse = apiService.sendNotification(
                        ApiService.NotificationRequest(
                            title = "Ada Detail Proker Baru nih!",
                            body = judulDetailProker
                        )
                    )
                    if (notifResponse.isSuccessful) {
                        Log.d("Notification", "Notification sent successfully")
                    } else {
                        Log.e("Notification", "Failed to send notification: ${notifResponse.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("Notification", "Error sending notification", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Detail Proker Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for new detail proker updates"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(judulDetailProker: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Detail Proker Baru")
            .setContentText("Detail proker baru telah ditambahkan: $judulDetailProker")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }
    }

    private suspend fun addProkerDetailToApi(
        token: String,
        idProker: String,
        judulDetailProker: String,
        tanggal: String,
        gambar: Uri?,
    ): Result<TambahDetailResponse> {
        return try {
            // Prepare request bodies
            val judulRequestBody = judulDetailProker.toRequestBody("text/plain".toMediaType())
            val tanggalRequestBody = tanggal.toRequestBody("text/plain".toMediaType())

            // Handle image if provided
            val imagePart = gambar?.let { uri ->
                val file = uriToFile(uri)

                val gambarReq = file.asRequestBody("image/*".toMediaType())
                MultipartBody.Part.createFormData("gambar", file.name, gambarReq)
            }


            // API call
            val response = apiService.addProkerDetail(
                token = "Bearer $token",
                idProker = idProker.toInt(),
                judulDetailProker = judulRequestBody,
                tanggal = tanggalRequestBody,
                gambar = imagePart
            )

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Function to convert URI to File
    private fun uriToFile(uri: Uri): File {
        val contentResolver = context.contentResolver
        val myFile = createCustomTempFile()

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(myFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return myFile
    }

    // Create a custom temp file to save the image
    private fun createCustomTempFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PROKER_${timeStamp}_", ".jpg", storageDir)
    }
}