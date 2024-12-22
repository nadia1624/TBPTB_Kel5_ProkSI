package com.example.proksi_tbptb.frontend.detail_proker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.DetailProker2Data
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class DetailProker2ViewModel : ViewModel() {
    private val _detailProker = MutableLiveData<DetailProker2Data?>()
    val detailProker: LiveData<DetailProker2Data?> = _detailProker

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val apiService = ApiConfig.api

    fun fetchDetailProker2(idDetailProker: Int, token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                Log.d("DetailProker2ViewModel", "Fetching details: idDetailProker = $idDetailProker, token = Bearer $token")
                val response = apiService.getDetailProker2(idDetailProker, "Bearer $token")
                println(response)

                if (response.isSuccessful) {
                    val detailProkerResponse = response.body()
                    if (detailProkerResponse != null) {
                        Log.d("DetailProker2ViewModel", "Response data: ${detailProkerResponse.data}")
                        _detailProker.value = detailProkerResponse.data
                    } else {
                        _error.value = "Data tidak tersedia"
                    }
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("DetailProker2ViewModel", "Error: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setError(message: String) {
        _error.value = message
    }

    fun clearError() {
        _error.value = null
    }

    // Factory for ViewModel
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailProker2ViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailProker2ViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
