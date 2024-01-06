package com.example.flatmatefinder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentCreatePasswordBinding

class CreatePassword : Fragment() {
    private var _binding : FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        val createPassword = binding.createPassword
        val back = binding.back
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        createPassword.setOnClickListener {
            startActivity(Intent(activity as LoginActivity, OnboardingActivity::class.java))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}