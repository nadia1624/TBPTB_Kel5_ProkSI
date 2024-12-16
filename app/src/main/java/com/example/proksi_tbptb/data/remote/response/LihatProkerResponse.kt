package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class LihatProkerResponse(

	@field:SerializedName("LihatProkerResponse")
	val lihatProkerResponse: List<LihatProkerResponseItem?>? = null
)

data class LihatProkerResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nama_proker")
	val namaProker: String? = null,

	@field:SerializedName("id_divisi")
	val idDivisi: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("divisi")
	val divisi: Divisi? = null,

	@field:SerializedName("id_proker")
	val idProker: Int,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,
	val id: Int
)

data class Divisi(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nama_divisi")
	val namaDivisi: String? = null,

	@field:SerializedName("id_divisi")
	val idDivisi: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
