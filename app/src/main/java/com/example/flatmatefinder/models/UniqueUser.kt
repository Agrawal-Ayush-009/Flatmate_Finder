package com.example.flatmatefinder.models

data class UniqueUser(
    val _id: String,
    val email: String,
    val name: String,
    val latestMessage: LatestMessage
)