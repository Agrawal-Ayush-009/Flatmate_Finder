package com.example.flatmatefinder.models

data class MessageAccessResponse(
    val likes: List<Like>,
    val uniqueUsers: List<UniqueUser>
)