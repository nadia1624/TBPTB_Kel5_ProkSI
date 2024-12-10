package com.example.proksi_tbptb.frontend.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proksi_tbptb.data.local.UserPreferences
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val userPreferences = UserPreferences()

    fun logout(context: Context) {
        viewModelScope.launch {
            userPreferences.logout(context)
        }
    }
}