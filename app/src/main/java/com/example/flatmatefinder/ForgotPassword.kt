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
import com.example.flatmatefinder.Utils.Constants
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.databinding.FragmentForgotPasswordBinding
import com.example.flatmatefinder.models.OTPRequest
import com.example.flatmatefinder.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPassword : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()
    private var finalEmail = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back = binding.back

        binding.sendOTP.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()){
                finalEmail = email

                authViewModel.sendOTP(OTPRequest(email))
            }else{
                Toast.makeText(activity as LoginActivity, "Please provide valid email", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()

    }

    private fun bindObserver(){
        authViewModel.otpResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = true
            when (it) {
                is NetworkResult.Success -> {
                    val activity = activity as LoginActivity
                    activity.email = finalEmail
                    findNavController().navigate(R.id.action_signUpFragment_to_OTPFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}