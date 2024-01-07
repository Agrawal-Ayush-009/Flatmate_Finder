package com.example.flatmatefinder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flatmatefinder.api.API
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.models.LoginResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor( private val api: API) {

    private val _loginResponseLivedata = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResponseLiveData : LiveData<NetworkResult<LoginResponse>> get() = _loginResponseLivedata

    suspend fun loginUser(loginRequest: LoginRequest){
        _loginResponseLivedata.postValue(NetworkResult.Loading())
        val response = api.login(loginRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _loginResponseLivedata.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _loginResponseLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _loginResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}