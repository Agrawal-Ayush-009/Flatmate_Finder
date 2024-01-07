package com.example.flatmatefinder.models

data class VerifyOTPRequest(
    val email: String,
    val otp: String
)