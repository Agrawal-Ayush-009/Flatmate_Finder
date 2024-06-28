package com.example.flatmatefinder.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flatmatefinder.models.Like_Dislike
import com.example.flatmatefinder.models.UpdateBioRequest
import com.example.flatmatefinder.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository : MainRepository ) : ViewModel(){

      val getFlatsLiveData = mainRepository.getFlatLiveData
      val getFlatMatesLiveData = mainRepository.getFlatMateLiveData
      val statusLiveData = mainRepository.statusLiveData
      val getUserDetailsLiveData = mainRepository.getUserDetailsResponseLiveData
      val updateBioLiveData = mainRepository.updateBioLiveData
      val deleteLiveData = mainRepository.deleteLiveData
      val getMessageAccessLiveData = mainRepository.getMessageAccessLiveData

      fun getMessageAccess(){
            viewModelScope.launch {
                  mainRepository.getMessageAccess()
            }
      }

      fun deleteAccount(){
            viewModelScope.launch {
                  mainRepository.deleteAccount()
            }
      }
      fun updateBio(updateBioRequest: UpdateBioRequest){
            viewModelScope.launch {
                  mainRepository.updateBio(updateBioRequest)
            }
      }
      fun getUserDetails(){
            viewModelScope.launch {
                  mainRepository.getUserDetails()
            }
      }

      fun getFlats(){
            viewModelScope.launch {
                  mainRepository.getFlats()
            }
      }

      fun getFlatmates(){
            viewModelScope.launch {
                  mainRepository.getFlatMates()
            }
      }
      fun like(likeDislike: Like_Dislike){
            viewModelScope.launch {
                  mainRepository.like(likeDislike)
            }
      }
      fun dislike_Flatmate(likeDislike: Like_Dislike){
            viewModelScope.launch {
                  mainRepository.dislike_Flatmate(likeDislike)
            }
      }
       fun dislike_Flat(likeDislike: Like_Dislike){
              viewModelScope.launch {
                     mainRepository.dislike_Flat(likeDislike)
              }
       }
}