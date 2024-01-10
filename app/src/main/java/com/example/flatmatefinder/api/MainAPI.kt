package com.example.flatmatefinder.api

import com.example.flatmatefinder.models.FlatCardInfo
import com.example.flatmatefinder.models.Like_Dislike
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainAPI {

       @GET("/flats")
       suspend fun getFlat(): Response<FlatCardInfo>

       @GET("/flatmates")
       suspend fun getFlatmates(): Response<FlatCardInfo>

       @POST("/add-like")
       suspend fun addLike(@Body likeDislike: Like_Dislike): Response<String>

       @POST("/dislike-flats")
       suspend fun dislikeFlats(@Body likeDislike: Like_Dislike): Response<String>

       @POST("/dislike-flatmates")
       suspend fun addDislikeFlatmates(@Body likeDislike: Like_Dislike): Response<String>

}