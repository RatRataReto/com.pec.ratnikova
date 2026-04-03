package com.pec.ratnikova.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://qr-app-server-g8wx.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

class StudentRepository(private val apiService: ApiService) {
    suspend fun login(code: String): Student? {
        return try {
            val response = apiService.login(LoginRequest(code))
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getStudent(code: String): Student? {
        return try {
            val response = apiService.getStudent(code)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun uploadAvatar(code: String, avatarUrl: String): Boolean {
        // Simplified for default avatars. In real app, we'd send a MultipartBody.Part
        return true 
    }
}
