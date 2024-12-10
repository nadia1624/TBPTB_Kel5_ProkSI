package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class IsiKegiatanResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("kegiatan")
	val kegiatan: KegiatanDetail? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("gambar")
	val gambar: Any? = null,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: Int? = null,

	@field:SerializedName("status_absensi")
	val statusAbsensi: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class KegiatanDetail(

	@field:SerializedName("nama_kegiatan")
	val namaKegiatan: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)
