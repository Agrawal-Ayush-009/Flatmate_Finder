package com.example.flatmatefinder.api

import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.models.LoginResponse
import com.example.flatmatefinder.models.OTPRequest
import com.example.flatmatefinder.models.OTPResponse
import com.example.flatmatefinder.models.SignUpRequest
import com.example.flatmatefinder.models.SignUpResponse
import com.example.flatmatefinder.models.VerifyOTPRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/send-otp")
    suspend fun sendOTP(@Body otpRequest: OTPRequest): Response<OTPResponse>

    @POST("/verify-otp")
    suspend fun verifyOTP(@Body verifyOTPRequest: VerifyOTPRequest): Response<OTPResponse>

    @POST("/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
}