package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllProkerResponse(
	val data: List<AllProkerResponseItem>
)

data class AllProkerResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("judul_detail_proker")
	val judulDetailProker: String? = null,

	@field:SerializedName("id_detailproker")
	val idDetailproker: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("id_proker")
	val idProker: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("proker")
	val proker: ProkerResponseItem
)

data class ProkerResponseItem(

	@field:SerializedName("id_proker")
	val idProker: Int? = null,

	@field:SerializedName("id_divisi")
	val idDivisi: Int? = null,

	@field:SerializedName("Divisi")
	val divisi: DivisiResponseItem

)

data class DivisiResponseItem(
	@field:SerializedName("id_divisi")
	val idDivisi: Int? = null,

	@field:SerializedName("nama_divisi")
	val namaDivisi: String? = null
)