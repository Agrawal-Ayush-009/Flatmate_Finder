package com.example.flatmatefinder.models

data class GoogleResponse(
    val newUser: Boolean,
    val status: String,
    val token: String
)