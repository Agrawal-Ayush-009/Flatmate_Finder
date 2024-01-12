package com.example.flatmatefinder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentFlatPictureBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class FlatPicture : Fragment() {
    private var _binding : FragmentFlatPictureBinding? = null
    private val binding get() = _binding!!
    private val CAM_PERM_CODE = 101
    private val CAM_REQ_CODE = 101
    private val GALLERY_REQUEST_CODE = 103
    private var currentPhotoPath :String = ""
    private var currentImageView : ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatPictureBinding.inflate(inflater, container, false)

        val next = binding.next
        val back = binding.back
        val image1 = binding.image1
        val image2 = binding.image2
        val image3 = binding.image3
        val image4 = binding.image4

        image1.setOnClickListener {
            currentImageView = image1
            openPopup()
        }
        image2.setOnClickListener {
            currentImageView = image2
            openPopup()
        }
        image3.setOnClickListener {
            currentImageView = image3
            openPopup()
        }
        image4.setOnClickListener {
            currentImageView = image4
            openPopup()
        }

        next.setOnClickListener {
            findNavController().navigate(R.id.action_flatPicture_to_flatInfo)
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun openPopup(){
        val popup = Dialog(requireContext())
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popup.setCancelable(true)
        popup.setContentView(R.layout.photo_select_popup)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popup.show()

        popup.findViewById<ImageView>(R.id.cameraButton).setOnClickListener {
            askCameraPermission()
            popup.dismiss()
        }

        popup.findViewById<ImageView>(R.id.galleryButton).setOnClickListener {
            Toast.makeText(requireContext(), "You Selected Gallery", Toast.LENGTH_SHORT).show()
            popup.dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAM_PERM_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera()
//                dispatchTakePictureIntent()
            }else{
                Toast.makeText(requireContext(), "Camera Permission is Required to Use Camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun askCameraPermission() {
        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.CAMERA), CAM_PERM_CODE)
        }else{
            openCamera()
//            dispatchTakePictureIntent()
        }
    }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, CAM_REQ_CODE)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAM_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){
                val image = data?.extras?.get("data") as Bitmap
                currentImageView?.setImageBitmap(image)
            }

        }

        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}