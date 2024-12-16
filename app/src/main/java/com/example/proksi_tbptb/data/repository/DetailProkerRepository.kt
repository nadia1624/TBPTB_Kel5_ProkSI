package com.example.proksi_tbptb.data.repository

import android.util.Log
import com.example.proksi_tbptb.data.remote.response.DetailProkerResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import retrofit2.Response

class DetailProkerRepository{
    suspend fun getDetailProker(token: String, prokerId: Int): Response<DetailProkerResponse>? {
        return try {
            Log.d("DetailProkerRepository", "Fetching details for ProkerID: $prokerId with Token: $token")
            val response = ApiConfig.api.getDetailProker(token = token, prokerId = prokerId)

            Log.d("DetailProkerRepository", "Response Code: ${response}")
            Log.d("DetailProkerRepository", "Response Body: ${response.body()}")

            response
        } catch (e: Exception) {
            Log.e("DetailProkerRepository", "Error fetching proker details", e)
            null
        }
    }
}

