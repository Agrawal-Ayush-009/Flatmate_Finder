package com.example.flatmatefinder

import android.R
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.flatmatefinder.databinding.FragmentHomeBinding


class Home : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var al = arrayListOf<String>()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewFlipper = binding.viewFlipper

       val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewFlipper.showNext()
                handler.postDelayed(this, 2000)

            }
        }, 2000)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}