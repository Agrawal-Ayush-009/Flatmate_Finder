package com.example.flatmatefinder

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentFlatBinding

class Flat : Fragment() {
    private var _binding: FragmentFlatBinding? = null
    private val binding get() = _binding!!
    private var clickedButton = arrayListOf<AppCompatButton>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatBinding.inflate(inflater, container, false)

        val next = binding.next
        val back = binding.back
        val yesButton = binding.YesButton
        val noButton = binding.NoButton
        val activity  = activity as OnboardingActivity

        yesButton.setOnClickListener {
            clickedButton.add(yesButton)
            yesButton.setTextColor(Color.parseColor("#FB893D"))
            noButton.setTextColor(Color.parseColor("#000000"))
            yesButton.setBackgroundDrawable(resources.getDrawable(R.drawable.clicked_button))
            noButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_option))
        }

        noButton.setOnClickListener {
            clickedButton.add(noButton)
            noButton.setTextColor(Color.parseColor("#FB893D"))
            yesButton.setTextColor(Color.parseColor("#000000"))
            noButton.setBackgroundDrawable(resources.getDrawable(R.drawable.clicked_button))
            yesButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_option))
        }

        next.setOnClickListener {
            if(clickedButton.isEmpty()){
                Toast.makeText(activity as OnboardingActivity, "Select an Option!",Toast.LENGTH_SHORT).show()
            }else{
                val button = clickedButton[clickedButton.size - 1]
                if((button.text.toString()) == "Yes"){
                    findNavController().navigate(R.id.action_flat_to_flatPicture)
                    activity.flat = true
                }else{
                    findNavController().navigate(R.id.action_flat_to_userPicture)
                    activity.flat = false
                }
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