package com.example.proksi_tbptb.frontend.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.remote.response.LoginResponse
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val isLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()
    val loginResponse = MutableLiveData<LoginResponse?>()
    private val apiService = ApiConfig.api

    // Fungsi login
    fun loginUser(email: String, password: String) {
        isLoading.value = true
        errorMessage.value = null

        // Menggunakan Retrofit untuk memanggil API
        val call = apiService.login(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    loginResponse.value = response.body()
                } else {
                    errorMessage.value = "Login failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = "Error: ${t.localizedMessage}"
            }
        })
    }
}
