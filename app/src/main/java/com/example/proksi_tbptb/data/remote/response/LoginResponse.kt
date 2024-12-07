package com.example.proksi_tbptb.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("id_user")
	val id_user: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
