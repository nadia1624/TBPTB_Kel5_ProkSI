package com.example.proksi_tbptb.data.remote.retrofit

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.proksi_tbptb.data.remote.response.CreateAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.DetailAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.LihatAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login (
        @Field("email") email: String,
        @Field("password") password : String
    ) : LoginResponse

    @GET("absensi")
    fun lihatAbsensi () : LihatAbsensiResponse

    @GET("absensi/detail/{id_rekapan}")
    fun detailAbsensi () : DetailAbsensiResponse

    @POST("absensi/create")
    fun createAbbsensi (
        @Field("gambar") gambar : String
    ) : CreateAbsensiResponse

}