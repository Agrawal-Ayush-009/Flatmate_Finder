package com.example.flatmatefinder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.api.MainAPI
import com.example.flatmatefinder.models.FlatCardInfo
import com.example.flatmatefinder.models.Like_Dislike
import com.example.flatmatefinder.models.StoreNameRequest
import com.example.flatmatefinder.models.StoreNameResponse
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainAPI: MainAPI) {

      private val _getFlatsMutableLiveData = MutableLiveData<NetworkResult<FlatCardInfo>>()
      val getFlatLiveData : LiveData<NetworkResult<FlatCardInfo>>
            get() = _getFlatsMutableLiveData



      private val _getFlatMatesMutableLiveData = MutableLiveData<NetworkResult<FlatCardInfo>>()
      val getFlatMateLiveData : LiveData<NetworkResult<FlatCardInfo>>
            get() = _getFlatMatesMutableLiveData
      // TODO: Implement a FlatMateCardInfo data class




      private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
       val statusLiveData : LiveData<NetworkResult<String>>
             get() = _statusLiveData



      suspend fun getFlats(){
            _getFlatsMutableLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.getFlat()

            if (response.isSuccessful && response.body() != null) {
                  _getFlatsMutableLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _getFlatsMutableLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _getFlatsMutableLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }

      suspend fun getFlatMates(){
            _getFlatsMutableLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.getFlatmates()

            if (response.isSuccessful && response.body() != null) {
                  _getFlatsMutableLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _getFlatsMutableLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _getFlatsMutableLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }

      suspend fun dislike_Flat(likeDislike: Like_Dislike){
            _statusLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.dislikeFlats(likeDislike)

            if (response.isSuccessful && response.body() != null) {
                  _statusLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _statusLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }

      suspend fun like(likeDislike: Like_Dislike){
            _statusLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.addLike(likeDislike)

            if (response.isSuccessful && response.body() != null) {
                  _statusLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _statusLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }

      suspend fun dislike_Flatmate(likeDislike: Like_Dislike){
            _statusLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.addDislikeFlatmates(likeDislike)

            if (response.isSuccessful && response.body() != null) {
                  _statusLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _statusLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }


}