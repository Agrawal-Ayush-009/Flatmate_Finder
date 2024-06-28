package com.example.flatmatefinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import com.example.flatmatefinder.Utils.Constants.SOCKET_URL
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.adaptors.LikesListAdapter
import com.example.flatmatefinder.adaptors.MessageListAdapter
import com.example.flatmatefinder.databinding.FragmentMessageBinding
import com.example.flatmatefinder.models.LatestMessage
import com.example.flatmatefinder.models.UniqueUser
import com.example.flatmatefinder.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

@AndroidEntryPoint
class Message : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    lateinit var socket: Socket
    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var adapterMessageList: MessageListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMessageBinding.inflate(inflater, container, false)

        try {
            socket = IO.socket(SOCKET_URL)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getMessageAccess()

        adapterMessageList = MessageListAdapter(
            requireContext(),
            arrayListOf(
                UniqueUser(
                    "wiondfef",
                    "osdheheif",
                    "name",
                    LatestMessage(69, "iwbnfr", true, "name", "hi", "hi", "today")
                )
            )
        )
        bindObserver()
    }

    private fun bindObserver() {
        mainViewModel.getMessageAccessLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            binding.transparentBg.visibility = View.GONE

            binding
            when (it) {
                is NetworkResult.Success -> {
                    val uniqueUser = it.data!!.uniqueUsers
                    adapterMessageList = MessageListAdapter(requireContext(), uniqueUser)
                    val messageList = binding.messageList

                    messageList.layoutManager = LinearLayoutManager(requireContext())
                    messageList.adapter = adapterMessageList

                    adapterMessageList.setOnItemClickListener(object : MessageListAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), ChatActivity::class.java)
                            intent.putExtra("name", uniqueUser[position].name)
                            intent.putExtra("id", uniqueUser[position]._id)
                            startActivity(intent)
                        }
                    })

                    val likes = it.data!!.likes
                    val adapterLikeList = LikesListAdapter(requireContext(), likes)
                    val likeList = binding.likeList
                    likeList.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    likeList.adapter = adapterLikeList
                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.transparentBg.visibility = View.VISIBLE
                }
            }
        }
    }

    private object CHAT_KEYS {
        const val NEW_MESSAGE = "new_message"
    }

    private fun initMain() {
        socket!!.on(CHAT_KEYS.NEW_MESSAGE) { args ->
            Log.d(
                "onNewMessageDebug",
                args[0].toString()
            )
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::socket.isInitialized) {
            socket.disconnect()
            socket.off(CHAT_KEYS.NEW_MESSAGE)
        }
        _binding = null
    }
}