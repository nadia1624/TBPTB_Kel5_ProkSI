package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProkerResponse(

	@SerializedName("data")
	val data: List<DetailProkerResponseItem?>? = null
)

data class DetailProkerResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("judul_detail_proker")
	val judulDetailProker: String? = null,

	@field:SerializedName("id_detailproker")
	val idDetailproker: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("gambar")
	val gambar: Any? = null,

	@field:SerializedName("id_proker")
	val idProker: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
