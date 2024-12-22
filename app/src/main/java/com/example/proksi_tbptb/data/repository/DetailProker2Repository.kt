package com.example.proksi_tbptb.data.repository

import com.example.proksi_tbptb.data.remote.response.DetailProker2Response
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig

class DetailProker2Repository {

    private val apiService = ApiConfig.api

    suspend fun getDetailProker2(idDetailProker: Int, token: String): DetailProker2Response? {
        val response = apiService.getDetailProker2(idDetailProker = idDetailProker, token = token)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }
}
