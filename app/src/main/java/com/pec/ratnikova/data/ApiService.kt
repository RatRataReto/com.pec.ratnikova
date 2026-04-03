package com.pec.ratnikova.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/qr/login")
    suspend fun login(@Body request: LoginRequest): Response<Student>

    @GET("api/qr/student/{code}")
    suspend fun getStudent(@Path("code") code: String): Response<Student>
}
