package com.example.proksi_tbptb.frontend.isikegiatan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.IsiKegiatanResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class IsiKegiatanViewModel : ViewModel() {

    private val _detailKegiatan = MutableLiveData<IsiKegiatanResponse?>()
    val detailKegiatan: LiveData<IsiKegiatanResponse?> = _detailKegiatan

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDetailKegiatan(token: String, idKegiatan: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.api.absensiKegiatan("Bearer $token", idKegiatan)
                if (response.isSuccessful) {
                    _detailKegiatan.value = response.body()
                    Log.d("IsiKegiatanViewModel", "Berhasil memuat data: ${response.body()}")
                } else {
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                    Log.e("IsiKegiatanViewModel", "Gagal memuat data: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.localizedMessage}"
                Log.e("IsiKegiatanViewModel", "Terjadi kesalahan: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
