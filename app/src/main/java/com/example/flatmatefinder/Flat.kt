package com.example.flatmatefinder

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
import com.example.flatmatefinder.databinding.FragmentFlatBinding
import com.example.flatmatefinder.models.FlatStatusRequest
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Flat : Fragment() {
    private var _binding: FragmentFlatBinding? = null
    private val binding get() = _binding!!
    private var clickedButton = arrayListOf<AppCompatButton>()

    private val onboardingViewModel by viewModels<OnboardingViewModel>()

    private var hasFlat: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    hasFlat = true
                    onboardingViewModel.flatStatus(FlatStatusRequest("true"))
                }else{
                    hasFlat = false
                    onboardingViewModel.flatStatus(FlatStatusRequest("false"))
                }
            }
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun bindObserver(){
        onboardingViewModel.flatStatusRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    if(hasFlat){
                        findNavController().navigate(R.id.action_flat_to_flatPicture)
                    }else{
                        findNavController().navigate(R.id.action_flat_to_userPicture)
                    }
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