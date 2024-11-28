package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class LihatAbsensiResponse(

	@field:SerializedName("LihatAbsensiResponse")
	val lihatAbsensiResponse: List<LihatAbsensiResponseItem?>? = null
)

data class LihatAbsensiResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id_rekapan")
	val idRekapan: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
