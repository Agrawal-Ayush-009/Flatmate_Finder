package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentOTPBinding

class OTPFragment : Fragment() {
    private var _binding : FragmentOTPBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOTPBinding.inflate(inflater, container, false)
        val verifyOTP = binding.verifyOTP
        val back = binding.back
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        verifyOTP.setOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_createPassword)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}