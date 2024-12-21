package com.example.proksi_tbptb.frontend.proker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.repository.LihatProkerRepository
import com.example.proksi_tbptb.frontend.proker.screen.ProkerItem
import kotlinx.coroutines.launch

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
                val response = repository.getProkers(token, divisiId)

                // Log the token and divisiId for debugging
                Log.d("LihatProkerViewModel", "Fetching prokers with Token: $token and DivisiID: $divisiId")

                if (response != null && response.isSuccessful) {
                    val prokerList = response.body()?.lihatProkerResponse?.mapNotNull { item ->
                        item?.let {
                            ProkerItem(
                                id = it.idProker, // Default to 0 if id is null
                                name = it.namaProker.orEmpty(), // Default to "" if namaProker is null
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
                    Log.e("LihatProkerViewModel", "Failed to fetch prokers, response: ${response?.message()}")
                    _error.value = "Error: ${response?.message() ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                Log.e("LihatProkerViewModel", "Error fetching prokers", e)
                _error.value = "Error: ${e.message ?: "An unknown error occurred"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
