package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentFlatInfo2Binding
import com.example.flatmatefinder.databinding.FragmentFlatInfoBinding

class FlatInfo2 : Fragment() {
    private var _binding : FragmentFlatInfo2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatInfo2Binding.inflate(inflater, container, false)
        val next = binding.next
        val back = binding.back

        next.setOnClickListener {
            findNavController().navigate(R.id.action_flatInfo2_to_userPicture)
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