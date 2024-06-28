package com.example.flatmatefinder.models

data class LatestMessage(
    val __v: Int,
    val _id: String,
    val read: Boolean,
    val `receiver`: String,
    val sender: String,
    val text: String,
    val timestamp: String
)