package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class RekapAbsenResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("kegiatan")
	val kegiatan: Kegiatan? = null,

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

data class Kegiatan(

	@field:SerializedName("tanggal_kegiatan")
	val tanggalKegiatan: String? = null,

	@field:SerializedName("nama_kegiatan")
	val namaKegiatan: String? = null,

	@field:SerializedName("jam_kegiatan")
	val jamKegiatan: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: Int? = null
)
