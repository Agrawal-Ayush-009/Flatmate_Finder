package com.example.flatmatefinder.models

data class FlatInfo(
    val _id: String,
    val address: Address,
    val branch: String,
    val capacity: Int,
    val drink: Boolean,
    val rent : Rent,
    val email: String,
    val flatImages: List<String>,
    val googlePicture: Any,
    val name: String,
    val nonVegetarian: Boolean,
    val occupied: Int,
    val profileImage: ProfileImage,
    val smoke: Boolean,
    val workout: Boolean,
    val bhk: Int,
    val year: Int
)