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
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.databinding.FragmentCreatePasswordBinding
import com.example.flatmatefinder.models.SignUpRequest
import com.example.flatmatefinder.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePassword : Fragment() {
    private var _binding : FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createPassword = binding.createPassword
        val back = binding.back
        val activity = activity as LoginActivity

        back.setOnClickListener {
            findNavController().popBackStack()
        }
        createPassword.setOnClickListener {
            val email = activity.email
            val password = binding.Password.text.toString()
            val passwordRepeat = binding.PasswordRepeat.text.toString()
            val validation = authViewModel.validateCredentials(email, password)
            if(validation.first){
                if(passwordRepeat != password){
                    Toast.makeText(activity,"Password doesn't match", Toast.LENGTH_SHORT).show()
                }else{
                    authViewModel.signUpUser(SignUpRequest(email, password))
                }
            }else{
                Toast.makeText(activity, validation.second, Toast.LENGTH_SHORT).show()
            }
        }

        bindObservers()
    }

    private fun bindObservers() {
        authViewModel.signUpRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    startActivity(Intent(activity as LoginActivity, OnboardingActivity::class.java))
                    (activity as LoginActivity).finish()
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