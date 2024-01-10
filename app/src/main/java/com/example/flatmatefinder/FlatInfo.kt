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
import com.example.flatmatefinder.databinding.FragmentFlatInfoBinding
import com.example.flatmatefinder.models.FlatInfoRequest1
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlatInfo : Fragment() {
    private var _binding : FragmentFlatInfoBinding? = null
    private val binding get() = _binding!!
    private val onboardingViewModel by viewModels<OnboardingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val next = binding.next
        val back = binding.back

        next.setOnClickListener {
            val flat = binding.inputFlatNo.text.toString()
            val area = binding.inputArea.text.toString()
            val additional = binding.inputAddInfo.text.toString()

            val monthlyAmount = binding.inputMonthlyAmount.text.toString()
            val brokerage = binding.inputBrokerageAmount.text.toString()

            if(flat.isEmpty() || area.isEmpty() || monthlyAmount.isEmpty() || brokerage.isEmpty()){
                Toast.makeText(activity as OnboardingActivity, "Enter proper Address and Rent", Toast.LENGTH_SHORT).show()
            }else{
                onboardingViewModel.storeFlatInfo1(FlatInfoRequest1(additional, area,
                    brokerage.toInt(), flat, monthlyAmount.toInt()))
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
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_flatInfo_to_flatInfo2)
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