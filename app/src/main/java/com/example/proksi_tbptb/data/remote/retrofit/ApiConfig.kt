package com.example.proksi_tbptb.data.remote.retrofit

import com.example.proksi_tbptb.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private val client = OkHttpClient.Builder()
        .connectTimeout(600, TimeUnit.SECONDS) // Timeout koneksi
        .readTimeout(600, TimeUnit.SECONDS)    // Timeout membaca data
        .writeTimeout(600, TimeUnit.SECONDS)   // Timeout menulis data
        .build()

    private const val BASE_URL = BuildConfig.BASE_URL

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}