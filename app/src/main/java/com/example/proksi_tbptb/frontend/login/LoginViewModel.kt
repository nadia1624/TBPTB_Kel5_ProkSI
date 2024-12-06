package com.example.proksi_tbptb.frontend.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import com.example.proksi_tbptb.data.remote.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val apiService: ApiService = ApiConfig.api, // Dependency injection or default
    private val userPreferences: UserPreferences = UserPreferences() // UserPreferences sebagai dependensi
) : ViewModel() {

    // Fungsi untuk melakukan login
    fun login(email: String, password: String, context: Context, onError: (String?) -> Unit) {
        // Cek jika email dan password kosong
        if (email.isBlank() || password.isBlank()) {
            onError("Email and password cannot be empty")
            return
        }

        // Menjalankan login secara asinkron menggunakan Coroutine
        viewModelScope.launch {
            try {
                // Memanggil API untuk login dan mendapatkan response
                val response = apiService.login(email, password)

                // Menyimpan token jika login berhasil
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse: LoginResponse? = response.body()
                    loginResponse?.token?.let {
                        userPreferences.saveToken(context, it)
                        Log.d("LoginViewModel", "Token: $it")
                        onError(null)  // Tidak ada error
                    }
                } else {
                    onError("Login failed: ${response.message()}")
                }
            } catch (e: HttpException) {
                // Menangani kesalahan HTTP
                onError("HTTP error: ${e.message() ?: "An error occurred"}")
            } catch (e: Exception) {
                // Menangani kesalahan lainnya
                onError("Unexpected error: ${e.message}")
            }
        }
    }
}
