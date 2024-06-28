package com.example.flatmatefinder.api

import com.example.flatmatefinder.models.FlatCardInfo
import com.example.flatmatefinder.models.Like_Dislike
import com.example.flatmatefinder.models.MessageAccessResponse
import com.example.flatmatefinder.models.OTPResponse
import com.example.flatmatefinder.models.UpdateBioRequest
import com.example.flatmatefinder.models.UpdateBioResponse
import com.example.flatmatefinder.models.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface MainAPI {

       @GET("/display/flats")
       suspend fun getFlat(): Response<FlatCardInfo>

       @GET("/display/flatmates")
       suspend fun getFlatmates(): Response<FlatCardInfo>

       @POST("/user/add-like")
       suspend fun addLike(@Body likeDislike: Like_Dislike): Response<OTPResponse>

       @POST("/user/dislike-flats")
       suspend fun dislikeFlats(@Body likeDislike: Like_Dislike): Response<OTPResponse>

       @POST("/user/dislike-flatmates")
       suspend fun addDislikeFlatmates(@Body likeDislike: Like_Dislike): Response<OTPResponse>

       @GET("/user/user-details")
       suspend fun getUserDetails(): Response<UserDetailsResponse>

       @POST("/user/store-bio")
       suspend fun updateBio(@Body updateBioRequest: UpdateBioRequest): Response<UpdateBioResponse>

       @DELETE("/user/delete-user")
       suspend fun deleteAccount(): Response<UpdateBioResponse>

       @GET("/user/messages-access")
       suspend fun getMessageAccess(): Response<MessageAccessResponse>

       @Multipart
       @POST("")
       suspend fun uploadFlatImages()


}