package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class IsiAbsenKegiatanResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("namaKegiatan")
	val namaKegiatan: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("statusAbsen")
	val statusAbsen: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: String? = null
)
