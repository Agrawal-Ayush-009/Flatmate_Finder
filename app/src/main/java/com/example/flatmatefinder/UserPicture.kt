package com.example.flatmatefinder

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.databinding.FragmentUserPictureBinding
import java.io.File

class UserPicture : Fragment() {
    private var _binding : FragmentUserPictureBinding? = null
    private val binding get() = _binding!!
    private val CAM_PERM_CODE = 101
    private var currentImageView : ImageView? = null
    lateinit var imageUri : Uri
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        currentImageView!!.setImageURI(null)
        currentImageView!!.setImageURI(imageUri)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserPictureBinding.inflate(inflater, container, false)
        val next = binding.next
        val back = binding.back

        imageUri = createImageUri()

        next.setOnClickListener {
            findNavController().navigate(R.id.action_userPicture_to_personalInfo)
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


    private fun createImageUri(): Uri {
        val image = File((activity as OnboardingActivity).applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile((activity as OnboardingActivity).applicationContext,
            "com.example.flatmatefinder.fileProvider", image)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAM_PERM_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                contract.launch(imageUri)
            }else{
                Toast.makeText(requireContext(), "Camera Permission is Required to Use Camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun askCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                CAM_PERM_CODE
            )
        } else {
            contract.launch(imageUri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _binding  = null
    }
}