package com.example.flatmatefinder.models

data class UserDetailsResponse(
    val address: Address,
    val bio: Any,
    val branch: String,
    val capacity: Int,
    val dob: Dob,
    val drink: Boolean,
    val email: String,
    val furnishingStatus: String,
    val gender: String,
    val hasFlat: Boolean,
    val name: String,
    val occupied: Int,
    val picture: Any,
    val rent: Rent,
    val smoke: Boolean,
    val workout: Boolean,
    val bhk: Int,
    val year: Int
)