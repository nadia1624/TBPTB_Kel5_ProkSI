package com.example.proksi_tbptb.frontend.detail_proker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.DetailProkerResponseItem
import com.example.proksi_tbptb.data.repository.DetailProkerRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailProkerViewModel(
    private val repository: DetailProkerRepository
) : ViewModel() {

    private val _detailProkers = MutableLiveData<List<DetailProkerResponseItem>>()
    val detailProkers: LiveData<List<DetailProkerResponseItem>> = _detailProkers

    private val _detailProkerStatus = MutableLiveData<String>("Not Started")
    val detailProkerStatus: LiveData<String> = _detailProkerStatus

    private val _isProkerDone = MutableLiveData<Boolean>(false)
    val isProkerDone: LiveData<Boolean> = _isProkerDone

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchDetailProker(token: String, prokerId: Int) {
        viewModelScope.launch {
            Log.d("DetailProkerViewModel", "Updating status with token: $token")
            _isLoading.value = true
            _error.value = null

            try {
                Log.d("DetailProkerViewModel", "Fetching details for ProkerID: $prokerId")
                Log.d("DetailProkerViewModel", "Using Token: Bearer $token")

                val response = repository.getDetailProker("Bearer $token", prokerId)

                if (response?.isSuccessful == true) {
                    Log.d("DetailProkerViewModel", "Successfully fetched details: ${response.body()}")
                    val detailProkers = response.body()?.data ?: emptyList()
                    _detailProkers.value = detailProkers.filterNotNull()
                } else {
                    Log.e("DetailProkerViewModel", "Failed to fetch details: ${response?.message()}")
                    _error.value = "Failed to fetch detail proker: ${response?.message()}"
                }
            } catch (e: Exception) {
                Log.e("DetailProkerViewModel", "Error fetching details", e)
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun formatDate(dateString: String?): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            dateString?.let {
                val date = inputFormat.parse(it)
                Log.d("DetailProkerViewModel", "Parsed Date: $date")
                date?.let { outputFormat.format(it) } ?: "-"
            } ?: "-"
        } catch (e: Exception) {
            Log.e("DetailProkerViewModel", "Date parsing failed: ${e.message}")
            "-"
        }
    }

    fun updateProkerStatus(context: Context, token: String, prokerId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.updateProkerStatus("Bearer $token", prokerId)
                if (response?.isSuccessful == true) {
                    _detailProkerStatus.value = "Done"
                    _isProkerDone.value = true
                } else {
                    _error.value = "Failed to update status: ${response?.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class DetailProkerFactory(private val repository: DetailProkerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailProkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailProkerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
