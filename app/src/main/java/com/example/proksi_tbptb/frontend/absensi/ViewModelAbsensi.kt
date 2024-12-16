package com.example.proksi_tbptb.frontend.absensi.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.response.LihatAbsensiResponseItem
import com.example.proksi_tbptb.data.repository.AbsensiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AbsensiViewModel : ViewModel() {

    private val repository = AbsensiRepository()

    private val _absensiList = MutableStateFlow<List<LihatAbsensiResponseItem>>(emptyList())
    val absensiList: StateFlow<List<LihatAbsensiResponseItem>> = _absensiList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val userPreferences = UserPreferences()

    // Ambil token dari UserPreferences
    private suspend fun getToken(context: Context): String? {
        return userPreferences.getToken(context)
    }

    fun fetchAbsensi(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                _isLoading.value = true
                _errorMessage.value = null
                val response = repository.getAbsensi(token)
                if (response?.isSuccessful == true) {
                    // Langsung akses body karena respons berupa daftar
                    _absensiList.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value =
                        "Failed to fetch absensi data: ${response?.errorBody()?.string()}"
                }
                _isLoading.value = false
            } else {
                _errorMessage.value = "Token not found, please login again."
            }
        }
    }
}
