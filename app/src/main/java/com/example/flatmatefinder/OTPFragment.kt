package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.databinding.FragmentOTPBinding
import com.example.flatmatefinder.models.VerifyOTPRequest
import com.example.flatmatefinder.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : Fragment() {
    private var _binding : FragmentOTPBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verifyOTP = binding.verifyOTP
        val back = binding.back
        val activity = activity as LoginActivity

        val emailDescription = binding.emailDescription

        val email = activity.email

        verifyOTP.setOnClickListener {
            val otp = binding.otp.text.toString()
            authViewModel.verifyOTP(VerifyOTPRequest(email,otp))
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObservers()
    }

    private fun bindObservers() {
        authViewModel.otpResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_OTPFragment_to_createPassword)
                    Toast.makeText(activity as LoginActivity, "OTP Verified", Toast.LENGTH_SHORT).show()
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