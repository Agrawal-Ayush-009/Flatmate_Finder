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
import com.example.flatmatefinder.databinding.FragmentDOBBinding
import com.example.flatmatefinder.models.StoreDOBRequest
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DOB : Fragment() {
    private var _binding: FragmentDOBBinding?  = null
    private val binding get() = _binding!!

    private val onboardingViewModel by viewModels<OnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDOBBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val next = binding.next
        val back = binding.back


        next.setOnClickListener {
            val dd = binding.dd.text.toString()
            val mm = binding.mm.text.toString()
            val yyyy = binding.yyyy.text.toString()

            onboardingViewModel.storeDOB(StoreDOBRequest("$dd $mm $yyyy"))
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun bindObserver(){
        onboardingViewModel.storeDOBRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    binding.dd.setText("").toString()
                    binding.mm.setText("").toString()
                    binding.yyyy.setText("").toString()
                    findNavController().navigate(R.id.action_DOB_to_gender)
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