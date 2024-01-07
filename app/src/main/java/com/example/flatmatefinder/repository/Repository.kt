package com.example.flatmatefinder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flatmatefinder.api.API
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.models.LoginResponse
import com.example.flatmatefinder.models.OTPRequest
import com.example.flatmatefinder.models.OTPResponse
import com.example.flatmatefinder.models.SignUpRequest
import com.example.flatmatefinder.models.SignUpResponse
import com.example.flatmatefinder.models.VerifyOTPRequest
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor( private val api: API) {

    private val _loginResponseLivedata = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResponseLiveData : LiveData<NetworkResult<LoginResponse>>
        get() = _loginResponseLivedata

    private val _otpResponseLivedata = MutableLiveData<NetworkResult<OTPResponse>>()
    val otpResponseLiveData : LiveData<NetworkResult<OTPResponse>>
        get() = _otpResponseLivedata

    private val _signUpResponseLivedata = MutableLiveData<NetworkResult<SignUpResponse>>()
    val signUpRequestLiveData : LiveData<NetworkResult<SignUpResponse>>
        get() = _signUpResponseLivedata


    suspend fun loginUser(loginRequest: LoginRequest){
        _loginResponseLivedata.postValue(NetworkResult.Loading())
        val response = api.login(loginRequest)
        handleResponse(response)
    }

    suspend fun sendOTP(otpRequest: OTPRequest){
        _otpResponseLivedata.postValue(NetworkResult.Loading())
        val response = api.sendOTP(otpRequest)

        if (response.isSuccessful && response.body() != null) {
            _otpResponseLivedata.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _otpResponseLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _otpResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun verifyOTP(verifyOTPRequest: VerifyOTPRequest){
        _otpResponseLivedata.postValue(NetworkResult.Loading())
        val response = api.verifyOTP(verifyOTPRequest)

        if(response.isSuccessful && response.body() != null){
            _otpResponseLivedata.postValue(NetworkResult.Success(response.body()))
        }else if(response.errorBody() != null){
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _otpResponseLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _otpResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun signUpUser(signUpRequest: SignUpRequest){
        _signUpResponseLivedata.postValue(NetworkResult.Loading())
        val response = api.signUp(signUpRequest)
        if (response.isSuccessful && response.body() != null) {
            _signUpResponseLivedata.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _signUpResponseLivedata.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _signUpResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
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