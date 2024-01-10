package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.databinding.FragmentMessageBinding
import com.example.flatmatefinder.databinding.FragmentOTPBinding

class Message : Fragment() {
    private var _binding : FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager : TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}