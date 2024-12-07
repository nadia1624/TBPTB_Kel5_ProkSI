package com.example.proksi_tbptb.frontend.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val apiService: ApiService = ApiConfig.api, // Dependency injection or default
    private val userPreferences: UserPreferences = UserPreferences()
) : ViewModel() {

    fun login(email: String, password: String, context: Context, onError: (String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onError("Email and password cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                val response = apiService.login(email, password)
                userPreferences.saveToken(context, response.token.toString())
                userPreferences.saveLoginState(context, true)
                userPreferences.saveUserId(context, response.id_user!!)
                onError(null)
                Log.d("LoginViewModel", "Token: ${response.token}")
                Log.d("LoginViewModel", "User ID: ${response.id_user}")
            } catch (e: HttpException) {
                onError("HTTP error: ${e.message() ?: "An error occurred"}")
            } catch (e: Exception) {
                onError("Unexpected error: ${e.message}")
            }
        }
    }
}
