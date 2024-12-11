package com.example.proksi_tbptb.frontend.Proker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.repository.LihatProkerRepository
import com.example.proksi_tbptb.frontend.Proker.screen.ProkerItem
import com.example.proksi_tbptb.data.remote.response.LihatProkerResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LihatProkerViewModel(private val repository: LihatProkerRepository) : ViewModel() {
    private val _prokers = MutableLiveData<List<ProkerItem>>()
    val prokers: LiveData<List<ProkerItem>> = _prokers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchProkers(token: String, divisiId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<LihatProkerResponse>? = repository.getProkers(token, divisiId)
                Log.d("LihatProkerViewModel", "Token: $token")
                Log.d("LihatProkerViewModel", "ID: $divisiId")

                if (response != null && response.isSuccessful) {
                    val prokerList = response.body()?.lihatProkerResponse?.mapNotNull { item ->
                        item?.let {
                            ProkerItem(
                                name = it.namaProker ?: "Unknown",
                                status = when (it.status) {
                                    0 -> "Not Started"
                                    1 -> "In Progress"
                                    2 -> "Done"
                                    else -> "Unknown"
                                }
                            )
                        }
                    } ?: emptyList()

                    _prokers.value = prokerList
                    _error.value = null
                } else {
                    Log.e("ProkerViewModel", "Response error: $response")
                    _error.value = "Error: ${response?.message() ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                Log.e("ProkerViewModel", "Error fetching prokers", e)
                _error.value = "Error: ${e.message ?: "An unknown error occurred"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}