package com.example.proksi_tbptb.frontend.ChangePassword

import android.content.Context
import androidx.collection.emptyLongSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel(){
    private val apiService = ApiConfig.api

    fun changePassword(
        token: String,
        passwordLama: String,
        passwordBaru: String,
        konfirmasiPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (!token.isNullOrEmpty()) {
                try {
                    val response = apiService.changePassword(
                        token = "Bearer $token",
                        passwordlama = passwordLama,
                        passwordBaru = passwordBaru,
                        konfirmasiPassword = konfirmasiPassword
                    )

                    if (response.isSuccessful){
                        onSuccess()
                    } else {
                        onError("Password gagal diubah")
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    onError("Terjadi kesalahan: ${e.message}")
                }
            }
        else {
            onError("Token tidak ditemukan")
        }
    }
    }
}