package com.example.flatmatefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Chat
import com.example.flatmatefinder.Utils.Constants.BASE_URL
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.adaptors.ChatAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {
    private var socket: Socket? = null

    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var chatAdapter: ChatAdapter

    private val _onNewChat = MutableLiveData<Chat>()
    val onNewChat: LiveData<Chat> get() = _onNewChat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatAdapter = ChatAdapter()

        val chatSectionRV = findViewById<RecyclerView>(R.id.chatSection)

        chatSectionRV.layoutManager = LinearLayoutManager(this)
        chatSectionRV.adapter = chatAdapter


        val back = findViewById<ImageView>(R.id.back)
        val nameTextView = findViewById<TextView>(R.id.name)

        val name = intent.getStringExtra("name")
        val receiverId = intent.getStringExtra("id").toString()

        nameTextView.text = name

        val sendButton = findViewById<TextView>(R.id.send)

        sendButton.setOnClickListener {
            val text = findViewById<EditText>(R.id.msgEditText).text.toString()
            if(text.isNotEmpty()){
                sendMessage(text, receiverId)
                findViewById<EditText>(R.id.msgEditText).setText("")
            }

        }


        back.setOnClickListener {
            finish()
        }

        try {
            val options = IO.Options()
            options.query = tokenManager.getToken()
            socket = IO.socket(BASE_URL, options)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        socket?.connect()

        socket?.on(Socket.EVENT_CONNECT) {
            socket?.emit("authenticate", tokenManager.getToken())
            Log.d(TAG, "Connected")
        }

        socket?.on(Socket.EVENT_DISCONNECT){
            Log.d(TAG, "Disconnected")
        }


        socket?.on("message") { args ->
            val data = args[0] as JSONObject

            val chat = Gson().fromJson(data.toString(), Chat::class.java)

            val sender = data.getString("sender")
            val text = data.getString("text")

            _onNewChat.postValue(chat)

            Log.d(TAG, "$sender : $text")
        }

        onNewChat.observe(this){
        }
    }

    private fun sendMessage(text: String, receiverId: String) {
        val data = JSONObject()
        data.put("senderToken", tokenManager.getToken())
        data.put("receiver", receiverId)
        data.put("text", text)
        socket?.emit("message", data)
        Toast.makeText(this, "MSG SENT", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.disconnect()
    }
}