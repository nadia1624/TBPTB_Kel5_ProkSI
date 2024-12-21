package com.example.proksi_tbptb.frontend.proker

import com.example.proksi_tbptb.data.repository.LihatProkerRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LihatProkerFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LihatProkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LihatProkerViewModel(LihatProkerRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}