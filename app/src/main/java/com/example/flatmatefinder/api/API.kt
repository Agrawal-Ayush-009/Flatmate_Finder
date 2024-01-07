package com.example.flatmatefinder.api

import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}