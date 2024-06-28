package com.example.flatmatefinder.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flatmatefinder.databinding.MsgItemBinding
import com.example.flatmatefinder.models.UniqueUser

class MessageListAdapter(val context: Context, val list: List<UniqueUser>): RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    inner class MessageListViewHolder(val binding: MsgItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener?.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageListAdapter.MessageListViewHolder {
        val binding = MsgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageListAdapter.MessageListViewHolder, position: Int) {
        holder.binding.Name.text = list[position].name
        holder.binding.latestMsg.text = list[position].latestMessage.text
    }
    override fun getItemCount(): Int {
        return list.size
    }

}