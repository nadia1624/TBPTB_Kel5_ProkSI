package com.example.proksi_tbptb.repository

interface JwtTokenManager {
    suspend fun saveAccessJwt(token: String)
    suspend fun getAccessJwt(): String?
    suspend fun clearAllTokens()
}