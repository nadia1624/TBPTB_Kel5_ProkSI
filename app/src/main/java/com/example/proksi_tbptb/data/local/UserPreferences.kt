package com.example.proksi_tbptb.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

//
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences {
    private val tokenKey = stringPreferencesKey("auth_token")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token
        }
    }

    suspend fun getToken(context: Context): String? {
        return context.dataStore.data.first()[tokenKey]
    }
}
