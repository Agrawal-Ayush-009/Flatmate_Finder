package com.example.flatmatefinder.api

import com.example.flatmatefinder.models.BranchYearRequest
import com.example.flatmatefinder.models.BranchYearResponse
import com.example.flatmatefinder.models.FlatImageUploadRequest
import com.example.flatmatefinder.models.FlatInfoRequest1
import com.example.flatmatefinder.models.FlatInfoRequest2
import com.example.flatmatefinder.models.FlatResponse
import com.example.flatmatefinder.models.FlatStatusRequest
import com.example.flatmatefinder.models.FlatStatusResponse
import com.example.flatmatefinder.models.GenderRequest
import com.example.flatmatefinder.models.GenderResponse
import com.example.flatmatefinder.models.LifestyleRequest
import com.example.flatmatefinder.models.ProfilePictureRequest
import com.example.flatmatefinder.models.StoreDOBRequest
import com.example.flatmatefinder.models.StoreDOBResponse
import com.example.flatmatefinder.models.StoreNameRequest
import com.example.flatmatefinder.models.StoreNameResponse
import com.example.flatmatefinder.models.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnboardingAPI {

    @POST("/user/store-name")
    suspend fun storeName(@Body storeNameRequest: StoreNameRequest): Response<StoreNameResponse>

    @POST("/user/store-dob")
    suspend fun storeDOB(@Body storeDOBRequest: StoreDOBRequest): Response<StoreDOBResponse>

    @POST("/user/store-flat-status")
    suspend fun flatStatus(@Body flatStatusRequest: FlatStatusRequest): Response<FlatStatusResponse>

    @POST("/user/store-gender")
    suspend fun storeGender(@Body genderRequest: GenderRequest): Response<GenderResponse>

    @POST("/user/store-branch-year")
    suspend fun storeBranchYear(@Body branchYearRequest: BranchYearRequest): Response<BranchYearResponse>

    @POST("/user/store-address-rent")
    suspend fun storeFlatInfo1(@Body flatInfoRequest1: FlatInfoRequest1): Response<FlatResponse>


    @POST("/user/store-furnishing-status-cap-occ")
    suspend fun storeFlatInfo2(@Body flatInfoRequest2: FlatInfoRequest2): Response<FlatResponse>

    @POST("/user/store-lifestyle")
    suspend fun storeLifestyle(@Body lifestyleRequest: LifestyleRequest): Response<FlatResponse>

    @POST("/user/upload-flat-images")
    suspend fun storeFlatImages(@Body flatImageUploadRequest: FlatImageUploadRequest): Response<FlatResponse>

    @POST("/user/upload-profile-image")
    suspend fun storeProfilePic(@Body profilePictureRequest: ProfilePictureRequest): Response<FlatResponse>
}
