package com.example.proksi_tbptb.frontend.TambahDetailProker

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.TambahDetailResponse
import com.example.proksi_tbptb.data.repository.TambahDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TambahDetailViewModel(
    private val repository: TambahDetailRepository
) : ViewModel() {
    private val _addProkerState = MutableStateFlow<Result<TambahDetailResponse>?>(null)
    val addProkerState: StateFlow<Result<TambahDetailResponse>?> = _addProkerState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun addProkerDetail(
        token: String,
        idProker: String,
        judulDetailProker: String,
        tanggal: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.addProkerDetail(
                    token = token,
                    idProker = idProker,
                    judulDetailProker = judulDetailProker,
                    tanggal = tanggal,
                    imageUri = imageUri
                )
                _addProkerState.value = result
            } catch (e: Exception) {
                _addProkerState.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}



