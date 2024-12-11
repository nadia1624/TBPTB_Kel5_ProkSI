package com.example.proksi_tbptb.data.repository

import android.util.Log
import com.example.proksi_tbptb.data.remote.response.LihatProkerResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import retrofit2.Response

class LihatProkerRepository {
    suspend fun getProkers(token: String, divisiId: Int): Response<LihatProkerResponse>? {
        return try {
            ApiConfig.api.lihatProker(
                token = "Bearer $token",
                divisiId = divisiId
            )
        } catch (e: Exception) {
            Log.e("Except", e.toString())
            e.printStackTrace()
            null
        }
    }
}
