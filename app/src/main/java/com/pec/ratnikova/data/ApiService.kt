package com.pec.ratnikova.data

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/qr/login")
    suspend fun login(@Body request: LoginRequest): Response<Student>

    @GET("api/qr/student/{code}")
    suspend fun getStudent(@Path("code") code: String): Response<Student>

    @Multipart
    @POST("api/qr/student/{code}/avatar")
    suspend fun updateAvatar(
        @Path("code") code: String,
        @Part file: MultipartBody.Part
    ): Response<Unit>
}
