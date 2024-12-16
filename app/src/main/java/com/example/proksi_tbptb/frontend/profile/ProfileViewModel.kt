package com.example.proksi_tbptb.frontend.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.response.DataUser
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userPreferences = UserPreferences()
    private val apiService = ApiConfig.api

    val profileState = mutableStateOf<DataUser?>(null) // Tambahkan state untuk menyimpan data profil

    fun logout(context: Context) {
        viewModelScope.launch {
            userPreferences.logout(context)
        }
    }

    fun getProfile(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                // Use .value to extract the actual token string
                val response = apiService.profile("Bearer $token", userId)
                if (response.isSuccessful) {
                    val profileResponse = response.body()
                    profileState.value = profileResponse?.data
                    Log.d("ProfileViewModel", "Profile data: ${profileResponse?.data}")
                } else {
                    Log.e("ProfileViewModel", "Profile API call failed: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching profile", e)
            }
        }
    }
}