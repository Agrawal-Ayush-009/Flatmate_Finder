package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentAcademicDetailsBinding

class AcademicDetails : Fragment() {
    private var _binding : FragmentAcademicDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcademicDetailsBinding.inflate(inflater, container, false)
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
            findNavController().navigate(R.id.action_academicDetails_to_flat)
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}