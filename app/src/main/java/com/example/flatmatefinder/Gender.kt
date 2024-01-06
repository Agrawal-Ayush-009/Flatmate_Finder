package com.example.flatmatefinder

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentGenderBinding

class Gender : Fragment() {
    private var _binding: FragmentGenderBinding?  = null
    private val binding get() = _binding!!
    private var clickedButton = arrayListOf<AppCompatButton>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGenderBinding.inflate(inflater, container, false)

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
                    Toast.makeText(activity as OnboardingActivity, "You are Male", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity as OnboardingActivity, "You are Female", Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(R.id.action_gender_to_academicDetails)
            }
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        clickedButton.clear()
    }

}