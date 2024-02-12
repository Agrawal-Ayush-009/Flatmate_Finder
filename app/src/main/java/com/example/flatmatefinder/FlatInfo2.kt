package com.example.flatmatefinder

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
import com.example.flatmatefinder.databinding.FragmentFlatInfo2Binding
import com.example.flatmatefinder.models.FlatInfoRequest2
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlatInfo2 : Fragment() {
    private var _binding : FragmentFlatInfo2Binding? = null
    private val binding get() = _binding!!
    private val onboardingViewModel by viewModels<OnboardingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatInfo2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val next = binding.next
        val back = binding.back
        var capacity = 1
        val slider = binding.occupiedSlider

        binding.ok.setOnClickListener {
            val c = binding.inputCapacity.text.toString().toInt()
            if(c < 1){
                Toast.makeText(activity as OnboardingActivity, "Enter value greater than equal to 1",
                    Toast.LENGTH_SHORT).show()
            }else{
                capacity = c;
                slider.value = 0F
                slider.valueFrom = 0F
                slider.valueTo = c.toFloat()
            }

        }

        next.setOnClickListener {
            val furnished = when(binding.radioInfo.checkedRadioButtonId){
                R.id.fullyFurnished -> "fullyFurnished"
                R.id.semiFurnished -> "semiFurnished"
                R.id.nonFurnished -> "nonFurnished"
                else -> {}
            }

            val capacity = capacity

            val occupied = slider.value

            val bhk = binding.inputSize.text.toString().toInt()

            onboardingViewModel.storeFlatInfo2(FlatInfoRequest2(capacity, furnished.toString(), bhk , occupied.toInt()))

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
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_flatInfo2_to_userPicture)
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