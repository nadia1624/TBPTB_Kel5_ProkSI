package com.example.proksi_tbptb.frontend.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.LoginResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Status Loading
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Pesan Error
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Response Login
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginResponse

    private val apiService = ApiConfig.api

    // Fungsi Login
    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null

        // Memanggil API secara Asinkron menggunakan Coroutine
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.loginSuspend(email, password)

                // Pastikan menggunakan 'response.body()' jika responsnya sukses
                if (response.isSuccessful) {
                    _loginResponse.postValue(response.body()) // Post value di thread utama
                } else {
                    _errorMessage.postValue("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
