package com.example.proksi_tbptb.data.repository

import android.util.Log
import com.example.proksi_tbptb.data.remote.response.LihatAbsensiResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AbsensiRepository {

    // Pastikan untuk menambahkan parameter token di sini
    suspend fun getAbsensi(token: String): Response<LihatAbsensiResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AbsensiRepository", "Fetching absensi data with token: Bearer $token")
                val response = ApiConfig.api.lihatAbsensi("Bearer $token")

                // Log detail respons
                if (response.isSuccessful) {
                    Log.d("AbsensiRepository", "Response body: ${response.body()}")
                } else {
                    Log.e("AbsensiRepository", "Response error: ${response.errorBody()?.string()}")
                }

                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AbsensiRepository", "Error fetching absensi data: ${e.localizedMessage}", e)
                null
            }
        }
    }
}

