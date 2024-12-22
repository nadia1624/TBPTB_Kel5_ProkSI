package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadProfileResponse(

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
