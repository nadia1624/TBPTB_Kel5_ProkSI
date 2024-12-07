package com.example.proksi_tbptb.frontend.kegiatan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.RekapAbsenResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class KegiatanViewModel : ViewModel() {
    private val _rekapAbsensi = MutableLiveData<RekapAbsenResponse?>()
    val rekapAbsensi: LiveData<RekapAbsenResponse?> get() = _rekapAbsensi

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchRiwayatAbsensi(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<RekapAbsenResponse> = ApiConfig.api.rekapAbsensi("Bearer $token", userId)
                Log.d("KegiatanViewModel", "Status code: ${response.code()}")
                Log.d("KegiatanViewModel", "Response body: ${response.body()}")
                Log.d("KegiatanViewModel", "Token: $token")
                Log.d("KegiatanViewModel", "User ID: $userId")
                if (response.isSuccessful) {
                    _rekapAbsensi.value = response.body()
                    Log.d("KegiatanViewModel", "Riwayat absensi berhasil diambil: ${response.body()}")
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                    Log.e("KegiatanViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.message}"
                Log.e("KegiatanViewModel", "Exception: ${e.message}")
            }
        }
    }
}
