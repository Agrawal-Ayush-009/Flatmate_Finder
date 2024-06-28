package com.example.flatmatefinder.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flatmatefinder.databinding.LikesLayoutBinding
import com.example.flatmatefinder.models.Like

class LikesListAdapter(val context: Context, val list: List<Like>) :
    RecyclerView.Adapter<LikesListAdapter.LikesListViewHolder>() {
    inner class LikesListViewHolder(val binding: LikesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikesListViewHolder {
        val binding = LikesLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return LikesListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LikesListViewHolder, position: Int) {

    }
}