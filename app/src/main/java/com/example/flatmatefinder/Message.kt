package com.example.flatmatefinder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.flatmatefinder.Utils.Constants.SOCKET_URL
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.databinding.FragmentMessageBinding
import com.example.flatmatefinder.databinding.FragmentOTPBinding
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException

class Message : Fragment() {
    private var _binding : FragmentMessageBinding? = null
    private val binding get() = _binding!!
    lateinit var socket : Socket
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMessageBinding.inflate(inflater, container, false)

        try{
            socket = IO.socket(SOCKET_URL)
        }catch (e : URISyntaxException){
            e.printStackTrace()
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private object CHAT_KEYS{
        const val NEW_MESSAGE = "new_message"
    }

    private fun initMain() {
        socket!!.on(CHAT_KEYS.NEW_MESSAGE
        ) { args -> Log.d("onNewMessageDebug", args[0].toString()) }


    }

    override fun onDestroy() {
        super.onDestroy()
        if(this::socket.isInitialized){
            socket.disconnect()
            socket.off(CHAT_KEYS.NEW_MESSAGE)
        }
        _binding = null
    }
}