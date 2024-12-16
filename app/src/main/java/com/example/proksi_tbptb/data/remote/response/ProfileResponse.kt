package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: DataUser? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataUser(

	@field:SerializedName("nama_depan")
	val namaDepan: String? = null,

	@field:SerializedName("jadwal")
	val jadwal: String? = null,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("jabatan")
	val jabatan: String? = null,

	@field:SerializedName("nama_belakang")
	val namaBelakang: String? = null,

	@field:SerializedName("gambar")
	val gambar: Any? = null,

	@field:SerializedName("divisi")
	val divisi: DivisiUser? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class DivisiUser(

	@field:SerializedName("nama_divisi")
	val namaDivisi: String? = null
)
