package com.example.flatmatefinder

import android.app.Dialog
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.databinding.FragmentFlatPictureBinding
import com.example.flatmatefinder.models.FlatImageUploadRequest
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.io.encoding.ExperimentalEncodingApi

@AndroidEntryPoint
class FlatPicture : Fragment() {
    private var _binding : FragmentFlatPictureBinding? = null
    private val binding get() = _binding!!
    private val onboardingViewModel by viewModels<OnboardingViewModel>()
    private val CAM_PERM_CODE = 101
    private val CAM_REQ_CODE = 101
    private val GALLERY_REQUEST_CODE = 103
    private var currentPhotoPath :String = ""
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
        _binding = FragmentFlatPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val next = binding.next
        val back = binding.back
        val image1 = binding.image1
        val image2 = binding.image2
        val image3 = binding.image3
        val image4 = binding.image4

        imageUri = createImageUri()

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
            val bitmap1 = convertImageToBitmap(image1)
            bitmap1.compress(Bitmap.CompressFormat.JPEG,40, FileOutputStream(image1.drawable.toString()))
//            val base64_1 = convertBitmapToBase64(bitmap1)
//            val base64_2 = convertBitmapToBase64(convertImageToBitmap(image2))
//            val base64_3 = convertBitmapToBase64(convertImageToBitmap(image3))
//            val base64_4 = convertBitmapToBase64(convertImageToBitmap(image4))
//
//            val files = arrayOf(base64_1, base64_2, base64_3, base64_4)
//            onboardingViewModel.storeFlatImages(FlatImageUploadRequest(files))
            findNavController().navigate(R.id.action_flatPicture_to_flatInfo)

        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindObserver(){
        onboardingViewModel.flatRequestLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    Toast.makeText(activity as OnboardingActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_flatPicture_to_flatInfo)
                }

                is NetworkResult.Error ->{
                    Toast.makeText(activity as OnboardingActivity, it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
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

    private fun createImageUri(): Uri{
        val image = File((activity as OnboardingActivity).applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile((activity as OnboardingActivity).applicationContext,
            "com.example.flatmatefinder.fileProvider", image)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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

    private fun convertImageToBitmap(image: ImageView): Bitmap {
        val drawable = image.drawable as BitmapDrawable
        return drawable.bitmap
    }
    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val stream =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,  50, stream)
        val image =  stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}