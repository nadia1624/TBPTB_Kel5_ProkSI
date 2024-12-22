package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(

	@field:SerializedName("data")
	val data: StatusData,

	@field:SerializedName("message")
	val message: String? = null
)

data class StatusData(

	@field:SerializedName("status_description")
	val statusDescription: String? = null,

	@field:SerializedName("id_proker")
	val idProker: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
