package com.example.flatmatefinder

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.example.flatmatefinder.databinding.FragmentPersonalInfoBinding
import com.example.flatmatefinder.models.LifestyleRequest
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfo : Fragment() {
    private var _binding : FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private var drinksArray = arrayListOf<AppCompatButton>()
    private var smokeArray = arrayListOf<AppCompatButton>()
    private var workoutArray = arrayListOf<AppCompatButton>()
    private var nonVegArray = arrayListOf<AppCompatButton>()

    private val onboardingViewModel by viewModels<OnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back= binding.back
        val next = binding.next

        val drinkYes = binding.drinkYes
        val drinkNo = binding.drinkNo

        val smokeYes = binding.smokeYes
        val smokeNo = binding.smokeNo

        val workoutYes = binding.workoutYes
        val workoutNo = binding.workoutNo

        val non_vegYes = binding.nonVegetarianYes
        val non_vegNo = binding.nonVegetarianNo

        val skip = binding.skip

        skip.setOnClickListener {
            startActivity(Intent(activity as OnboardingActivity, MainActivity::class.java))
        }

        drinkYes.setOnClickListener {
            drinksArray.add(drinkYes)
            drinkYes.setTextColor(Color.parseColor("#39DD00"))
            drinkNo.setTextColor(Color.parseColor("#000000"))
            drinkYes.setBackgroundDrawable(resources.getDrawable(R.drawable.yes_button))
            drinkNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
        }

        drinkNo.setOnClickListener {
            drinksArray.add(drinkNo)
            drinkYes.setTextColor(Color.parseColor("#000000"))
            drinkNo.setTextColor(Color.parseColor("#D63835"))
            drinkYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
            drinkNo.setBackgroundDrawable(resources.getDrawable(R.drawable.no_button))
        }


        smokeYes.setOnClickListener {
            smokeArray.add(smokeYes)
            smokeYes.setTextColor(Color.parseColor("#39DD00"))
            smokeNo.setTextColor(Color.parseColor("#000000"))
            smokeYes.setBackgroundDrawable(resources.getDrawable(R.drawable.yes_button))
            smokeNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
        }


        smokeNo.setOnClickListener {
            smokeArray.add(smokeNo)
            smokeYes.setTextColor(Color.parseColor("#000000"))
            smokeNo.setTextColor(Color.parseColor("#D63835"))
            smokeYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
            smokeNo.setBackgroundDrawable(resources.getDrawable(R.drawable.no_button))
        }

        workoutYes.setOnClickListener {
            workoutArray.add(workoutYes)
            workoutYes.setTextColor(Color.parseColor("#39DD00"))
            workoutNo.setTextColor(Color.parseColor("#000000"))
            workoutYes.setBackgroundDrawable(resources.getDrawable(R.drawable.yes_button))
            workoutNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
        }

        workoutNo.setOnClickListener {
            workoutArray.add(workoutNo)
            workoutYes.setTextColor(Color.parseColor("#000000"))
            workoutNo.setTextColor(Color.parseColor("#D63835"))
            workoutYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
            workoutNo.setBackgroundDrawable(resources.getDrawable(R.drawable.no_button))
        }

        non_vegYes.setOnClickListener {
            nonVegArray.add(non_vegYes)
            non_vegYes.setTextColor(Color.parseColor("#39DD00"))
            non_vegNo.setTextColor(Color.parseColor("#000000"))
            non_vegYes.setBackgroundDrawable(resources.getDrawable(R.drawable.yes_button))
            non_vegNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
        }

        non_vegNo.setOnClickListener {
            nonVegArray.add(non_vegNo)
            non_vegYes.setTextColor(Color.parseColor("#000000"))
            non_vegNo.setTextColor(Color.parseColor("#D63835"))
            non_vegYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_2))
            non_vegNo.setBackgroundDrawable(resources.getDrawable(R.drawable.no_button))
        }



        next.setOnClickListener {
            if(workoutArray.isEmpty() || drinksArray.isEmpty() || smokeArray.isEmpty() || nonVegArray.isEmpty()){
                Toast.makeText(activity as OnboardingActivity, "All Options are mandatory!" ,Toast.LENGTH_SHORT).show()
            }else{
                var drink = false
                var smoke = false
                var nonVeg = false
                var gym = false

                if(drinksArray[drinksArray.size - 1].text.toString() == "Yes"){
                    drink = true
                }
                if(smokeArray[smokeArray.size - 1].text.toString() == "Yes"){
                    smoke = true
                }
                if(nonVegArray[nonVegArray.size - 1].text.toString() == "Yes"){
                    nonVeg = true
                }
                if(workoutArray[workoutArray.size - 1].text.toString() == "Yes"){
                    gym = true
                }

                onboardingViewModel.storeLifestyle(LifestyleRequest(drink, nonVeg, smoke, gym))
            }
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun bindObserver(){
        onboardingViewModel.flatRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    Toast.makeText(activity as OnboardingActivity, "User Onboarding Completed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(activity as OnboardingActivity, MainActivity::class.java))
                    (activity as OnboardingActivity).finish()
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
    }
}