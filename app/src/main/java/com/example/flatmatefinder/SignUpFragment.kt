package com.example.flatmatefinder

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.databinding.FragmentSignUpBinding
import com.example.flatmatefinder.models.OTPRequest
import com.example.flatmatefinder.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    private var finalEmail = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = binding.redirectLogin
        val sendOTP = binding.sendOTP
        sendOTP.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            Log.d(TAG, "onViewCreated: " + email)
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()){
                finalEmail = email
                Log.d(TAG, "final: " + finalEmail)
                authViewModel.sendOTP(OTPRequest(email))
            }else{
                Toast.makeText(activity as LoginActivity, "Please provide valid email", Toast.LENGTH_SHORT).show()
            }
        }
        login.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObservers()
    }

    private fun bindObservers() {
        authViewModel.otpResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val activity = activity as LoginActivity
                    activity.email = finalEmail
                    Log.d(TAG, "bindObservers: hello")
                    findNavController().navigate(R.id.action_signUpFragment_to_OTPFragment)
                    Log.d(TAG, "bindObservers: hello2")
                    Toast.makeText(activity as LoginActivity, "OTP Sent Successfully", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity as LoginActivity, it.msg, Toast.LENGTH_SHORT).show()

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}