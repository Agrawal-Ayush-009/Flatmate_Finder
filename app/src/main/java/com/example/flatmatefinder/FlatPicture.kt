package com.example.flatmatefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentFlatPictureBinding

class FlatPicture : Fragment() {
    private var _binding : FragmentFlatPictureBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatPictureBinding.inflate(inflater, container, false)

        val next = binding.next
        val back = binding.back

        next.setOnClickListener {
            findNavController().navigate(R.id.action_flatPicture_to_flatInfo)
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