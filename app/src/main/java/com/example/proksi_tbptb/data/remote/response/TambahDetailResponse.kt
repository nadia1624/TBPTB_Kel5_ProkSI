package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName


data class TambahDetailResponse(
	val message: String,
	val data: TambahDetailData
)

data class TambahDetailData(

	@SerializedName("id_detailproker")
	val idDetailProker: Int,

	@SerializedName("id_proker")
	val idProker: String,

	@SerializedName("judul_detail_proker")
	val judulDetailProker: String,

	@SerializedName("tanggal")
	val tanggal: String,

	@SerializedName("gambar")
	val gambar: String,

	@SerializedName("updatedAt")
	val updatedAt: String,

	@SerializedName("createdAt")
	val createdAt: String
)
