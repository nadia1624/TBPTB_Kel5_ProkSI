package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProker2Response(

	@field:SerializedName("data")
	val data: DetailProker2Data,

	@field:SerializedName("message")
	val message: String? = null
)

data class DetailProker2Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("judul_detail_proker")
	val judulDetailProker: String? = null,

	@field:SerializedName("id_detailproker")
	val id_detailproker: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("id_proker")
	val idProker: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
