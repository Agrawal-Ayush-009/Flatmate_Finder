package com.example.flatmatefinder

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.databinding.FragmentGenderBinding
import com.example.flatmatefinder.models.GenderRequest
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Gender : Fragment() {
    private var _binding: FragmentGenderBinding?  = null
    private val binding get() = _binding!!
    private var clickedButton = arrayListOf<AppCompatButton>()

    private val onboardingViewModel by viewModels<OnboardingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val next = binding.next
        val back = binding.back
        val maleButton = binding.maleButton
        val femaleButton = binding.femaleButton

        maleButton.setOnClickListener {
            clickedButton.add(maleButton)
            maleButton.setTextColor(Color.parseColor("#FB893D"))
            femaleButton.setTextColor(Color.parseColor("#000000"))
            maleButton.setBackgroundDrawable(resources.getDrawable(R.drawable.clicked_button))
            femaleButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_option))
        }

        femaleButton.setOnClickListener {
            clickedButton.add(femaleButton)
            femaleButton.setTextColor(Color.parseColor("#FB893D"))
            maleButton.setTextColor(Color.parseColor("#000000"))
            femaleButton.setBackgroundDrawable(resources.getDrawable(R.drawable.clicked_button))
            maleButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_option))
        }

        next.setOnClickListener {
            if(clickedButton.isEmpty()){
                Toast.makeText(activity as OnboardingActivity, "Select an Option!", Toast.LENGTH_SHORT).show()
            }else{
                val button = clickedButton[clickedButton.size - 1]
                if((button.text.toString()) == "Male"){
                    onboardingViewModel.storeGender(GenderRequest("male"))
                    Log.d("KING" , "onViewCreated: male")
                }else{
                    onboardingViewModel.storeGender(GenderRequest("female"))
                }
            }
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun bindObserver(){
        onboardingViewModel.storeGenderRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_gender_to_academicDetails)
                }

                is NetworkResult.Error ->{
                    Toast.makeText(activity as OnboardingActivity, it.msg, Toast.LENGTH_SHORT).show()
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
        clickedButton.clear()
    }

}