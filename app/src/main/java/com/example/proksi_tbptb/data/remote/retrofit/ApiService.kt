package com.example.proksi_tbptb.data.remote.retrofit

import com.example.proksi_tbptb.data.remote.response.CreateAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.DetailAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.DetailProkerResponse
import com.example.proksi_tbptb.data.remote.response.IsiAbsenKegiatanResponse
import com.example.proksi_tbptb.data.remote.response.IsiKegiatanResponse
import com.example.proksi_tbptb.data.remote.response.LihatAbsensiResponse
import com.example.proksi_tbptb.data.remote.response.LihatProkerResponse
import com.example.proksi_tbptb.data.remote.response.LoginResponse
import com.example.proksi_tbptb.data.remote.response.ProfileResponse
import com.example.proksi_tbptb.data.remote.response.RekapAbsenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("absensi")
    suspend fun lihatAbsensi(
        @Header("Authorization") token: String
    ): Response<LihatAbsensiResponse>

    @GET("absensi/detail/{id_rekapan}")
    suspend fun detailAbsensi(
        @Header("Authorization") token: String,
        @Path("id_rekapan") idRekapan: Int
    ): Response<DetailAbsensiResponse>


    @FormUrlEncoded
    @POST("absensi/create")
    suspend fun createAbsensi(
        @Field("gambar") gambar: String
    ): Response<CreateAbsensiResponse>

    @GET("api/riwayat-absensi")
    suspend fun rekapAbsensi (
        @Header("Authorization") token : String,
        @Query("userId") userId: Int
    ) : Response<RekapAbsenResponse>

    @GET("api/kegiatan/{id_kegiatan}")
    suspend fun absensiKegiatan(
        @Header("Authorization") token: String,
        @Path("id_kegiatan") id_kegiatan: Int // Gunakan @Path karena id_kegiatan ada di URL
    ): Response<IsiKegiatanResponse>

    @Multipart
    @POST("api/kegiatan/{id_kegiatan}/absensi")
    suspend fun isiAbsensiKegiatan(
        @Header("Authorization") token: String,
        @Path("id_kegiatan") id_kegiatan: Int,
        @Part("userId") userId: RequestBody,
        @Part gambar: MultipartBody.Part
    ): Response<IsiAbsenKegiatanResponse>

    @Headers("Content-Type: application/json")
    @GET("profil/profile/data")
    suspend fun profile(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int
    ): Response<ProfileResponse>

    @GET("proker/")
    suspend fun lihatProker(
        @Header("Authorization") token: String,
        @Query("id_divisi") divisiId: Int // Optional query parameter
    ): Response<LihatProkerResponse>

    @GET("proker/detailproker/{id}")
    suspend fun getDetailProker(
        @Header("Authorization") token: String,
        @Path("id") prokerId: Int
    ): Response<DetailProkerResponse>
}
