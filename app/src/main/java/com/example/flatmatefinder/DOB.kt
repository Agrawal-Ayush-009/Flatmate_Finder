package com.example.flatmatefinder

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentDOBBinding
import com.example.flatmatefinder.databinding.FragmentNameBinding

class DOB : Fragment() {
    private var _binding: FragmentDOBBinding?  = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDOBBinding.inflate(inflater, container, false)
        val next = binding.next
        val back = binding.back
        val dd = binding.dd
        val mm = binding.mm
        val yyyy = binding.yyyy

        next.setOnClickListener {
            findNavController().navigate(R.id.action_DOB_to_gender)
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