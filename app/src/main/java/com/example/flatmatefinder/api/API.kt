package com.example.flatmatefinder.api

import com.example.flatmatefinder.ForgotPasswordOTP
import com.example.flatmatefinder.models.GoogleResponse
import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.models.LoginResponse
import com.example.flatmatefinder.models.OTPRequest
import com.example.flatmatefinder.models.OTPResponse
import com.example.flatmatefinder.models.SignUpRequest
import com.example.flatmatefinder.models.SignUpResponse
import com.example.flatmatefinder.models.StoreNameRequest
import com.example.flatmatefinder.models.StoreNameResponse
import com.example.flatmatefinder.models.VerifyOTPRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/user/send-otp")
    suspend fun sendOTP(@Body otpRequest: OTPRequest): Response<OTPResponse>

    @POST("/user/verify-otp")
    suspend fun verifyOTP(@Body verifyOTPRequest: VerifyOTPRequest): Response<OTPResponse>

    @POST("/user/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @GET("/user/google-auth")
    suspend fun continueGoogle()

    @POST("/user/forgot-password-otp")
    suspend fun forgotPasswordSendOTP(@Body otpRequest: OTPRequest): Response<OTPResponse>

    @POST("/user/forgot-password-otp-verify")
    suspend fun forgotPasswordVerifyOTP(@Body verifyOTPRequest: VerifyOTPRequest): Response<OTPResponse>

    @POST("/user/forgot-password-set")
    suspend fun setNewPassword(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>


}