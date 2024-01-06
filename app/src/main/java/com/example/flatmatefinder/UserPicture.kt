package com.example.flatmatefinder

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentUserPictureBinding

class UserPicture : Fragment() {
    private var _binding : FragmentUserPictureBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserPictureBinding.inflate(inflater, container, false)
        val next = binding.next
        val back = binding.back

        next.setOnClickListener {
            findNavController().navigate(R.id.action_userPicture_to_personalInfo)
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _binding  = null
    }
}