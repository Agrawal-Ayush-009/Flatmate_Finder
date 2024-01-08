package com.example.flatmatefinder.models

data class LifestyleRequest(
    val drink: Boolean,
    val nonVegitarian: Boolean,
    val smoke: Boolean,
    val workout: Boolean
)