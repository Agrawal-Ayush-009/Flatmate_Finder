package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.databinding.FragmentAcademicDetailsBinding
import com.example.flatmatefinder.models.BranchYearRequest
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcademicDetails : Fragment() {
    private var _binding : FragmentAcademicDetailsBinding? = null
    private val binding get() = _binding!!

    private val onboardingViewModel by viewModels<OnboardingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcademicDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val next = binding.next
        val back = binding.back
        val yearSpinner = binding.yearSpinner
        val branchSpinner = binding.branchSpinner

        ArrayAdapter.createFromResource(
            activity as OnboardingActivity, R.array.year_items,
            R.layout.spinner_style
        ).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            activity as OnboardingActivity, R.array.branch_items,
            R.layout.spinner_style
        ).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            branchSpinner.adapter = adapter
        }

        next.setOnClickListener {
            var year = yearSpinner.selectedItem.toString()
            val branch = branchSpinner.selectedItem.toString()
            if(year == "In which year are you currently studying?" || branch == "What is your branch?"){
                Toast.makeText(activity as OnboardingActivity, "Please select your Year and Branch", Toast.LENGTH_SHORT).show()
            }else{
                when(year){
                    "First year" -> onboardingViewModel.storeBranchYear(BranchYearRequest(branch, 1))
                    "Second year" -> onboardingViewModel.storeBranchYear(BranchYearRequest(branch, 2))
                    "Third year" -> onboardingViewModel.storeBranchYear(BranchYearRequest(branch, 3))
                    "Fourth year" -> onboardingViewModel.storeBranchYear(BranchYearRequest(branch, 4))
                }
            }

        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun bindObserver(){
        onboardingViewModel.branchYearRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_academicDetails_to_flat)
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