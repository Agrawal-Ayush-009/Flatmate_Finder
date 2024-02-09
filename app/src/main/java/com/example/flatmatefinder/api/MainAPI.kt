package com.example.flatmatefinder.api

import com.example.flatmatefinder.models.FlatCardInfo
import com.example.flatmatefinder.models.Like_Dislike
import com.example.flatmatefinder.models.UpdateBioRequest
import com.example.flatmatefinder.models.UpdateBioResponse
import com.example.flatmatefinder.models.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

       @GET("/user-details")
       suspend fun getUserDetails(): Response<UserDetailsResponse>

       @POST("/store-bio")
       suspend fun updateBio(@Body updateBioRequest: UpdateBioRequest): Response<UpdateBioResponse>

       @DELETE("/delete-user")
       suspend fun deleteAccount(): Response<UpdateBioResponse>

}