package com.example.flatmatefinder.adaptors

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.flatmatefinder.R
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
            val monthly = list[position].rent.monthlyAmount
            val brokerage = list[position].rent.brokerage

            holder.binding.rent.text = "₹$monthly + $brokerage(brokerage)"
            holder.binding.occupied.text = "$occupied/$capacity occupied"
            holder.binding.ownerName.text = list[position].name
            holder.binding.AddressLine1.text = list[position].address.flat
            holder.binding.AddressLine2.text = list[position].address.area
            holder.binding.Course.text = "${list[position].branch}  ${list[position].year} yr"
            holder.binding.flatSize.text = "${list[position].bhk}BHK"

            if(list[position].nonVegetarian){
                  holder.binding.vegLogo.setImageResource(R.drawable.non_veg_logo)
            }else{
                  holder.binding.vegLogo.setImageResource(R.drawable.veg_logo)
            }
            if(list[position].smoke){
                  holder.binding.smokeLogo.isVisible = true
            }
            if(list[position].drink){
                  holder.binding.drinkLogo.isVisible = true
            }
            if(list[position].workout){
                  holder.binding.gymLogo.isVisible = true
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
//            val profileBitmap = BitmapFactory.decodeByteArray(Base64.decode(ownerImage,
//                  Base64.DEFAULT), 0, Base64.decode(ownerImage, Base64.DEFAULT).size)
//            holder.binding.ownerPhoto.setImageBitmap(profileBitmap)



      }

      override fun getItemCount(): Int {
                return list.size
      }

}