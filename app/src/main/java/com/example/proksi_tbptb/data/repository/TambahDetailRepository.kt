package com.example.proksi_tbptb.data.repository

import android.content.Context
import android.net.Uri
import com.example.proksi_tbptb.data.remote.response.TambahDetailResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class TambahDetailRepository(
    private val apiService: ApiService,
    private val context: Context // Tambahkan context sebagai parameter constructor
) {
    suspend fun addProkerDetail(
        token: String,
        idProker: String,
        judulDetailProker: String,
        tanggal: String,
        imageUri: Uri?
    ): Result<TambahDetailResponse> {
        return try {
            // Prepare multipart data
            val judulRequestBody = judulDetailProker.toRequestBody("text/plain".toMediaType())
            val tanggalRequestBody = tanggal.toRequestBody("text/plain".toMediaType())

            // Handle image if provided
            val imagePart = imageUri?.let { uri ->
                // Buat temporary file
                val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

                // Copy data dari Uri ke File
                context.contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }

                // Buat MultipartBody.Part dari File
                val requestBody = file.asRequestBody("image/*".toMediaType())
                MultipartBody.Part.createFormData("image", file.name, requestBody)
            }

            val response = apiService.addProkerDetail(
                token = "Bearer $token",
                idProker = idProker,
                judulDetailProker = judulRequestBody,
                tanggal = tanggalRequestBody,
                image = imagePart
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
}