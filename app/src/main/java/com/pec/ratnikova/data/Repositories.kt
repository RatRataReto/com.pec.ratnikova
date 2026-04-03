package com.pec.ratnikova.data

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Base64

object RetrofitClient {
    private const val BASE_URL = "https://qr-app-server-g8wx.onrender.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
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
        return try {
            // avatarUrl expected format: "data:image/jpeg;base64,xxxx..."
            // Remove the prefix to get raw base64
            val base64Data = avatarUrl.substringAfter(",")
            val imageBytes = Base64.decode(base64Data, Base64.NO_WRAP)
            
            val requestFile = imageBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", "avatar.jpg", requestFile)
            
            val response = apiService.updateAvatar(code, body)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
