package com.example.proksi_tbptb.data.remote.retrofit

import com.example.proksi_tbptb.data.remote.response.CreateAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.DetailAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.LihatAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("absensi")
    suspend fun lihatAbsensi(
        @Header("Authorization") token: String
    ): Response<LihatAbsensiResponse>

    @GET("absensi/detail/{id_rekapan}")
    suspend fun detailAbsensi(): Response<DetailAbsensiResponse>

    @FormUrlEncoded
    @POST("absensi/create")
    suspend fun createAbsensi(
        @Field("gambar") gambar: String
    ): Response<CreateAbsensiResponse>
}
