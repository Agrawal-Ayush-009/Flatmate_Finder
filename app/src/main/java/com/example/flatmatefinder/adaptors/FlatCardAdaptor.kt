package com.example.flatmatefinder.adaptors

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flatmatefinder.databinding.FlatCardBinding
import com.example.flatmatefinder.models.FlatInfo

class FlatCardAdaptor(val context: Context, val list: List<FlatInfo>): RecyclerView.Adapter<FlatCardAdaptor.FlatCardViewHolder>(){

       inner class FlatCardViewHolder(val binding: FlatCardBinding): RecyclerView.ViewHolder(binding.root){

       }
      override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
      ): FlatCardAdaptor.FlatCardViewHolder {
                val binding = FlatCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return FlatCardViewHolder(binding)
      }

      override fun onBindViewHolder(holder: FlatCardAdaptor.FlatCardViewHolder, position: Int) {
            val capacity = list[position].capacity
            val occupied = list[position].occupied
            holder.binding.occupied.text = "$occupied/$capacity occupied"
            holder.binding.ownerName.text = list[position].name
            holder.binding.AddressLine1.text = list[position].address.flat
            holder.binding.AddressLine2.text = list[position].address.area
            holder.binding.rent.text = "₹${list[position].capacity+list[position].occupied}"
            holder.binding.Course.text = list[position].branch
            holder.binding.year.text = list[position].year.toString()
            holder.binding.branch.text = list[position].branch
            if(list[position].nonVegetarian){
                  holder.binding.vegLogo.visibility = ViewGroup.GONE
            }
            if(list[position].smoke){
                  holder.binding.smokeLogo.visibility = ViewGroup.VISIBLE
            }
            if(list[position].drink){
                  holder.binding.drinkLogo.visibility = ViewGroup.VISIBLE
            }
            if(list[position].workout){
                  holder.binding.gymLogo.visibility = ViewGroup.VISIBLE
            }
            val viewFlipper = holder.binding.viewFlipper

            val handler = Handler()
            handler.postDelayed(object : Runnable {
                  override fun run() {
                        viewFlipper.showNext()
                        handler.postDelayed(this, 2000)

                  }
            }, 2000)

//            val ownerImage = list[position].profileImage.data
//            Base64.decode(ownerImage, Base64.DEFAULT)
//            val profileBitmap = BitmapFactory.decodeByteArray(Base64.decode(ownerImage, Base64.DEFAULT), 0, Base64.decode(ownerImage, Base64.DEFAULT).size)
//            holder.binding.ownerPhoto.setImageBitmap(profileBitmap)



      }

      override fun getItemCount(): Int {
                return list.size
      }

}