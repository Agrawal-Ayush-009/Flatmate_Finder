package com.example.flatmatefinder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.api.OnboardingAPI
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
import org.json.JSONObject
import javax.inject.Inject

class OnboardingRepository @Inject constructor(private val onboardingAPI: OnboardingAPI) {
    private val _storeNameRequestLivedata = MutableLiveData<NetworkResult<StoreNameResponse>>()
    val storeNameRequestLiveData : LiveData<NetworkResult<StoreNameResponse>>
        get() = _storeNameRequestLivedata


    private val _storeDOBRequestLivedata = MutableLiveData<NetworkResult<StoreDOBResponse>>()
    val storeDOBRequestLiveData: LiveData<NetworkResult<StoreDOBResponse>>
        get() = _storeDOBRequestLivedata


    private val _flatStatusRequestLivedata = MutableLiveData<NetworkResult<FlatStatusResponse>>()
    val flatStatusRequestLiveData : LiveData<NetworkResult<FlatStatusResponse>>
        get() = _flatStatusRequestLivedata

    private val _storeGenderRequestLivedata = MutableLiveData<NetworkResult<GenderResponse>>()
    val  storeGenderRequestLiveData: LiveData<NetworkResult<GenderResponse>>
        get() = _storeGenderRequestLivedata


    private val _branchYearRequestLivedata = MutableLiveData<NetworkResult<BranchYearResponse>>()
    val  branchYearRequestLiveData: LiveData<NetworkResult<BranchYearResponse>>
        get() = _branchYearRequestLivedata



    private val _flatRequestLivedata = MutableLiveData<NetworkResult<FlatResponse>>()
    val flatRequestLiveData: LiveData<NetworkResult<FlatResponse>>
        get() = _flatRequestLivedata

    suspend fun storeFlatImages(flatImageUploadRequest: FlatImageUploadRequest){
        _flatRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeFlatImages(flatImageUploadRequest)

        if(response.isSuccessful && response.body() != null){
            _flatRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _flatRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _flatRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun storeProfilePic(profilePictureRequest: ProfilePictureRequest){
        _flatRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeProfilePic(profilePictureRequest)
        if(response.isSuccessful && response.body() != null){
            _flatRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _flatRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _flatRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


    suspend fun storeFlatInfo1(flatInfoRequest1: FlatInfoRequest1){
        _flatRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeFlatInfo1(flatInfoRequest1)

        if(response.isSuccessful && response.body() != null){
            _flatRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _flatRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _flatRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun storeLifestyle(lifestyleRequest: LifestyleRequest){
        _flatRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeLifestyle(lifestyleRequest)

        if(response.isSuccessful && response.body() != null){
            _flatRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _flatRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else {
            _flatRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun storeFlatInfo2(flatInfoRequest2: FlatInfoRequest2){
        _flatRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeFlatInfo2(flatInfoRequest2)

        if(response.isSuccessful && response.body() != null){
            _flatRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _flatRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _flatRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun storeBranchYear(branchYearRequest: BranchYearRequest){
        _branchYearRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeBranchYear(branchYearRequest)

        if(response.isSuccessful && response.body() != null){
            _branchYearRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _branchYearRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _branchYearRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


    suspend fun storeGender(genderRequest: GenderRequest){
        _storeGenderRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeGender(genderRequest)
        if(response.isSuccessful && response.body() != null){
            _storeGenderRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _storeDOBRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _storeDOBRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


    suspend fun flatStatus(flatStatusRequest: FlatStatusRequest){
        _flatStatusRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.flatStatus(flatStatusRequest)
        if(response.isSuccessful && response.body() != null){
            _flatStatusRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _flatStatusRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _flatStatusRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun storeDOB(storeDOBRequest: StoreDOBRequest){
        _storeDOBRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeDOB(storeDOBRequest)

        if(response.isSuccessful && response.body() != null){
            _storeDOBRequestLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _storeDOBRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        }else{
            _storeNameRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


    suspend fun storeName(storeNameRequest: StoreNameRequest){
        _storeNameRequestLivedata.postValue(NetworkResult.Loading())
        val response = onboardingAPI.storeName(storeNameRequest)

        if (response.isSuccessful && response.body() != null) {
            _storeNameRequestLivedata.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _storeNameRequestLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _storeNameRequestLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}