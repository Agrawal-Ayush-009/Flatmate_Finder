package com.example.flatmatefinder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val signUp = binding.redirectSignUp
        val login = binding.logIn
        val googleLogin = binding.googleLogin

        login.setOnClickListener {
            startActivity(Intent(activity as LoginActivity, MainActivity::class.java))
        }

        googleLogin.setOnClickListener {
            startActivity(Intent(activity as LoginActivity, OnboardingActivity::class.java))
        }
        signUp.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}