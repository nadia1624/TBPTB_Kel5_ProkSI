package com.example.proksi_tbptb.frontend.absensi_terkirim

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.DetailAbsensiResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AbsensiTerkirimViewModel : ViewModel() {
    private val _absensiDetailState =
        MutableStateFlow<AbsensiDetailState>(AbsensiDetailState.Loading)
    val absensiDetailState: StateFlow<AbsensiDetailState> get() = _absensiDetailState

    private val apiService = ApiConfig.api

    fun fetchDetailAbsensi(token: String, idRekapan: Int) {
        viewModelScope.launch {
            _absensiDetailState.value = AbsensiDetailState.Loading
            try {
                val response = apiService.detailAbsensi("Bearer $token", idRekapan)
                Log.d("AbsensiTerkirimViewModel", "Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let {
                        _absensiDetailState.value = AbsensiDetailState.Success(it)
                        Log.d("AbsensiTerkirim", response.toString())
                    } ?: run {
                        _absensiDetailState.value =
                            AbsensiDetailState.Error("Response body is empty")
                    }
                } else {
                    _absensiDetailState.value =
                        AbsensiDetailState.Error("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: HttpException) {
                _absensiDetailState.value = AbsensiDetailState.Error("HttpException: ${e.message}")
            } catch (e: IOException) {
                _absensiDetailState.value = AbsensiDetailState.Error("Network Error: ${e.message}")
            } catch (e: Exception) {
                _absensiDetailState.value =
                    AbsensiDetailState.Error("Unexpected Error: ${e.message}")
            }
        }
    }


    sealed class AbsensiDetailState {
        object Loading : AbsensiDetailState()
        data class Success(val detail: DetailAbsensiResponse) : AbsensiDetailState()
        data class Error(val message: String) : AbsensiDetailState()
    }
}
