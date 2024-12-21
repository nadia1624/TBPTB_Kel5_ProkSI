package com.example.proksi_tbptb.frontend.allproker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.AllProkerResponseItem
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllProkerViewModel : ViewModel() {

    private val _allProker = MutableStateFlow<List<AllProkerResponseItem>>(emptyList())
    val allProker: StateFlow<List<AllProkerResponseItem>> get() = _allProker

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val apiService = ApiConfig.api

    fun fetchAllDetailProker(token: String) {
        viewModelScope.launch {
            if (token.isNotEmpty()) {
                _isLoading.value = true
                _isSuccess.value = false
                _isError.value = false
                _errorMessage.value = null

                try {
                    val response = apiService.allProker(token = "Bearer $token")
                    _allProker.value = response.data
                    _isSuccess.value = true
                } catch (e: Exception) {
                    e.printStackTrace()
                    _isError.value = true
                    _errorMessage.value = "Terjadi kesalahan: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            } else {
                _isError.value = true
                _errorMessage.value = "Token tidak ditemukan"
            }
        }
    }
}
