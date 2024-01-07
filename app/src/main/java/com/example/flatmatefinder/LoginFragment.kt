package com.example.flatmatefinder

import android.content.Intent
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
import com.example.flatmatefinder.databinding.FragmentLoginBinding
import com.example.flatmatefinder.models.LoginRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUp = binding.redirectSignUp
        val login = binding.logIn
        val googleLogin = binding.googleLogin

        login.setOnClickListener {
            val validationResult = validateInput()
            if(validationResult.first){
                val email = binding.inputEmail.text.toString()
                val password = binding.PasswordInput.text.toString()
                authViewModel.loginUser(LoginRequest(email, password))
            }else{
                Toast.makeText(activity as LoginActivity, validationResult.second, Toast.LENGTH_SHORT).show()
            }
        }

        googleLogin.setOnClickListener {
            startActivity(Intent(activity as LoginActivity, OnboardingActivity::class.java))
        }
        signUp.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        bindObservers()
    }

    private fun validateInput(): Pair<Boolean, String>{
        val email = binding.inputEmail.text.toString()
        val password = binding.PasswordInput.text.toString()
        return authViewModel.validateCredentials(email, password)
    }
    private fun bindObservers() {
        authViewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //token
                    startActivity(Intent(activity as LoginActivity, MainActivity::class.java))
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