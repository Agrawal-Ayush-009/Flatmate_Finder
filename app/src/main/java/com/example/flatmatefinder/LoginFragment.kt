package com.example.flatmatefinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.api.API
import com.example.flatmatefinder.databinding.FragmentLoginBinding
import com.example.flatmatefinder.models.LoginRequest
import com.example.flatmatefinder.viewModels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    val RC_SIGN_IN = 9001
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    lateinit var api: API
    lateinit var mGoogleSignInClient: GoogleSignInClient

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (tokenManager.getToken() != null && tokenManager.getToken() != "") {
            startActivity(Intent(activity as LoginActivity, MainActivity::class.java))
            (activity as LoginActivity).finish()
        }
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("316208084302-cetgqeedat9rdj76dmbruqp00e182bdn.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUp = binding.redirectSignUp
        val login = binding.logIn
        val googleLogin = binding.googleLogin
        val forgotPassword = binding.forgotPassword

        val emailInput = binding.inputEmail
        emailInput.setBackgroundResource(R.drawable.edit_text_2)

        login.setOnClickListener {
            val validationResult = validateInput()
            if (validationResult.first) {
                val email = binding.inputEmail.text.toString()
                val password = binding.PasswordInput.text.toString()
                authViewModel.loginUser(LoginRequest(email, password))
            } else {
                Toast.makeText(
                    activity as LoginActivity,
                    validationResult.second,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        googleLogin.setOnClickListener {
            signIn()
        }

        signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassword2)
        }

        bindObservers()
    }

    private fun validateInput(): Pair<Boolean, String> {
        val email = binding.inputEmail.text.toString()
        val password = binding.PasswordInput.text.toString()
        return authViewModel.validateCredentials(email, password)
    }


    private fun bindObservers() {
        authViewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    startActivity(Intent(activity as LoginActivity, MainActivity::class.java))
                    (activity as LoginActivity).finish()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity as LoginActivity, it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                else -> {}
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            Log.d(TAG, "handleSignInResult:" + idToken.toString())
            updateUI(account)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }



    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

//    override fun onStart() {
//        super.onStart()
//        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
//        updateUI(account)
//    }

    private fun updateUI(account: GoogleSignInAccount?) {
        try {
            Log.d(TAG, "updateUI:" + account!!.email)
        }catch (e : NullPointerException){
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(requireContext(), "UpdateUI", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}