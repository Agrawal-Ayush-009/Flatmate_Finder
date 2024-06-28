package com.example.flatmatefinder.repository

import android.app.admin.NetworkEvent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.api.MainAPI
import com.example.flatmatefinder.models.FlatCardInfo
import com.example.flatmatefinder.models.Like_Dislike
import com.example.flatmatefinder.models.MessageAccessResponse
import com.example.flatmatefinder.models.OTPResponse
import com.example.flatmatefinder.models.StoreNameRequest
import com.example.flatmatefinder.models.StoreNameResponse
import com.example.flatmatefinder.models.UpdateBioRequest
import com.example.flatmatefinder.models.UpdateBioResponse
import com.example.flatmatefinder.models.UserDetailsResponse
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainAPI: MainAPI) {

      private val _getUserDetailsLiveData = MutableLiveData<NetworkResult<UserDetailsResponse>>()
      val getUserDetailsResponseLiveData : LiveData<NetworkResult<UserDetailsResponse>>
            get() = _getUserDetailsLiveData


      private val _updateBioLiveData = MutableLiveData<NetworkResult<UpdateBioResponse>>()
      val updateBioLiveData : LiveData<NetworkResult<UpdateBioResponse>>
            get() = _updateBioLiveData


      private val _getFlatsMutableLiveData = MutableLiveData<NetworkResult<FlatCardInfo>>()
      val getFlatLiveData : LiveData<NetworkResult<FlatCardInfo>>
            get() = _getFlatsMutableLiveData



      private val _getFlatMatesMutableLiveData = MutableLiveData<NetworkResult<FlatCardInfo>>()
      val getFlatMateLiveData : LiveData<NetworkResult<FlatCardInfo>>
            get() = _getFlatMatesMutableLiveData
      // TODO: Implement a FlatMateCardInfo data class




      private val _statusLiveData = MutableLiveData<NetworkResult<OTPResponse>>()
       val statusLiveData : LiveData<NetworkResult<OTPResponse>>
             get() = _statusLiveData


      private val _deleteLiveData = MutableLiveData<NetworkResult<UpdateBioResponse>>()
      val deleteLiveData : LiveData<NetworkResult<UpdateBioResponse>>
            get() = _deleteLiveData


      private val _getMessageAccessLiveData = MutableLiveData<NetworkResult<MessageAccessResponse>>()
      val getMessageAccessLiveData: LiveData<NetworkResult<MessageAccessResponse>>
            get() = _getMessageAccessLiveData

      suspend fun getMessageAccess(){
            _getMessageAccessLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.getMessageAccess()
            if (response.isSuccessful && response.body() != null) {
                  _getMessageAccessLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _getMessageAccessLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _getMessageAccessLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }

      suspend fun deleteAccount(){
            _deleteLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.deleteAccount()
            if (response.isSuccessful && response.body() != null) {
                  _deleteLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _deleteLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _deleteLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }
      suspend fun updateBio(updateBioRequest: UpdateBioRequest){
            _updateBioLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.updateBio(updateBioRequest)
            if (response.isSuccessful && response.body() != null) {
                  _updateBioLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _updateBioLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _updateBioLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }
      suspend fun getUserDetails(){
            _getUserDetailsLiveData.postValue(NetworkResult.Loading())
            val response = mainAPI.getUserDetails()
            Log.d(TAG, "getUserDetails: repo")
            if (response.isSuccessful && response.body() != null) {
                  _getUserDetailsLiveData.postValue(NetworkResult.Success(response.body()))
            } else if (response.errorBody() != null) {
                  val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                  _getUserDetailsLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
            } else {
                  _getUserDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
      }

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