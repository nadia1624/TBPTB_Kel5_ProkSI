package com.example.proksi_tbptb.data.repository

import android.util.Log
import com.example.proksi_tbptb.data.remote.response.DetailProkerResponse
import com.example.proksi_tbptb.data.remote.response.StatusResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import retrofit2.Response

class DetailProkerRepository {

    // Mendapatkan detail proker
    suspend fun getDetailProker(token: String, prokerId: Int): Response<DetailProkerResponse>? {
        return try {
            Log.d("DetailProkerRepository", "Fetching details for ProkerID: $prokerId with Token: $token")
            val response = ApiConfig.api.getDetailProker(token = token, prokerId = prokerId)

            Log.d("DetailProkerRepository", "Response Code: ${response.code()}")
            Log.d("DetailProkerRepository", "Response Body: ${response.body()}")

            response
        } catch (e: Exception) {
            Log.e("DetailProkerRepository", "Error fetching proker details", e)
            null
        }
    }

    // Update status proker
    suspend fun updateProkerStatus(token: String, prokerId: Int): Response<StatusResponse>? {
        return try {
            Log.d("DetailProkerRepository", "Updating status for ProkerID: $prokerId with Token: $token")
            val response = ApiConfig.api.updateProkerStatus(token = token, prokerId = prokerId)

            if (response.isSuccessful) {
                Log.d("DetailProkerRepository", "Successfully updated status: ${response.body()}")
            } else {
                Log.e("DetailProkerRepository", "Failed to update status. Error: ${response.message()}")
            }

            response
        } catch (e: Exception) {
            Log.e("DetailProkerRepository", "Error updating proker status", e)
            null
        }
    }
}



