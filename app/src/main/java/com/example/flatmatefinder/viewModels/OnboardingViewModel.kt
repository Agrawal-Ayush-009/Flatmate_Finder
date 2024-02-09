package com.example.flatmatefinder.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flatmatefinder.Utils.NetworkResult
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
import com.example.flatmatefinder.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val onboardingRepository: OnboardingRepository): ViewModel(){

     val storeNameRequestLiveData: LiveData<NetworkResult<StoreNameResponse>>
        get() = onboardingRepository.storeNameRequestLiveData

    val storeDOBRequestLiveData: LiveData<NetworkResult<StoreDOBResponse>>
        get() = onboardingRepository.storeDOBRequestLiveData

    val flatStatusRequestLiveData: LiveData<NetworkResult<FlatStatusResponse>>
        get() = onboardingRepository.flatStatusRequestLiveData

    val  storeGenderRequestLiveData: LiveData<NetworkResult<GenderResponse>>
        get() = onboardingRepository.storeGenderRequestLiveData

    val branchYearRequestLiveData: LiveData<NetworkResult<BranchYearResponse>>
        get() = onboardingRepository.branchYearRequestLiveData

    val flatRequestLiveData: LiveData<NetworkResult<FlatResponse>>
        get() = onboardingRepository.flatRequestLiveData

    fun storeProfilePic(profilePictureRequest: ProfilePictureRequest){
        viewModelScope.launch {
            onboardingRepository.storeProfilePic(profilePictureRequest)
        }
    }

    fun storeFlatImages(flatImageUploadRequest: FlatImageUploadRequest){
        viewModelScope.launch {
            onboardingRepository.storeFlatImages(flatImageUploadRequest)
        }
    }
    fun storeFlatInfo1(flatInfoRequest1: FlatInfoRequest1){
        viewModelScope.launch {
            onboardingRepository.storeFlatInfo1(flatInfoRequest1)
        }
    }

    fun storeFlatInfo2(flatInfoRequest2: FlatInfoRequest2){
        viewModelScope.launch{
            onboardingRepository.storeFlatInfo2(flatInfoRequest2)
        }
    }

    fun storeLifestyle(lifestyleRequest: LifestyleRequest){
        viewModelScope.launch {
            onboardingRepository.storeLifestyle(lifestyleRequest)
        }
    }

    fun storeBranchYear(branchYearRequest: BranchYearRequest){
        viewModelScope.launch {
            onboardingRepository.storeBranchYear(branchYearRequest)
        }
    }

    fun storeGender(genderRequest: GenderRequest){
        viewModelScope.launch {
            onboardingRepository.storeGender(genderRequest)
        }
    }

    fun flatStatus(flatStatusRequest: FlatStatusRequest){
        viewModelScope.launch {
            onboardingRepository.flatStatus(flatStatusRequest)
        }
    }


    fun storeName(storeNameRequest: StoreNameRequest){
        viewModelScope.launch {
            onboardingRepository.storeName(storeNameRequest)
        }
    }

    fun storeDOB(storeDOBRequest: StoreDOBRequest){
        viewModelScope.launch {
            onboardingRepository.storeDOB(storeDOBRequest)
        }
    }
}