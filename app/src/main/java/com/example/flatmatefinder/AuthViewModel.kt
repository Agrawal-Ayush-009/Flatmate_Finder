package com.example.flatmatefinder

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.models.LoginResponse
import com.example.flatmatefinder.repository.Repository
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val loginResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = repository.loginResponseLiveData

    fun loginUser(loginRequest: LoginRequest){
        viewModelScope.launch {
            repository.loginUser(loginRequest)
        }

    }

    fun validateCredentials(email: String, password: String): Pair<Boolean, String>{
        var result = Pair(true, "")
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide all credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false, "Please provide valid email")
        }else if(password.length <= 5){
            result = Pair(false, "Password length should be greater that 5")
        }

        return result
    }

}