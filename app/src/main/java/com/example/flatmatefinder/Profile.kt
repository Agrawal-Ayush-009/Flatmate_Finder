
package com.example.flatmatefinder

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.databinding.FragmentProfileBinding
import com.example.flatmatefinder.models.UpdateBioRequest
import com.example.flatmatefinder.viewModels.AuthViewModel
import com.example.flatmatefinder.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Profile : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getUserDetails()

        val editProfile = binding.editProfile
        val editBio = binding.editBio
        val saveBio = binding.saveBio
        editProfile.setOnClickListener {
            startActivity(Intent(activity as MainActivity, EditProfileActivity::class.java))
        }

        saveBio.setOnClickListener {
            val bio = editBio.text.toString()
            if(bio.isEmpty()){
                Toast.makeText(requireContext(), "Bio Can't be Empty", Toast.LENGTH_SHORT).show()
            }else{
                mainViewModel.updateBio(UpdateBioRequest(bio))
            }
        }

        binding.options.setOnClickListener {
            openPopup()
        }

        bindObserver()
    }

    private fun openPopup(){
        val popup = Dialog(requireContext())
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popup.setCancelable(true)
        popup.setContentView(R.layout.options_popup)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popup.show()

        popup.findViewById<TextView>(R.id.logOut).setOnClickListener {
            tokenManager.saveToken("")
            Toast.makeText(requireContext(), "Log Out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            (activity as MainActivity).finish()
            popup.dismiss()
        }

        popup.findViewById<TextView>(R.id.deleteUser).setOnClickListener {
            mainViewModel.deleteAccount()
            popup.dismiss()
        }

        popup.findViewById<TextView>(R.id.shareFlatify).setOnClickListener {
            Toast.makeText(requireContext(), "Sharing Flatify", Toast.LENGTH_SHORT).show()
            popup.dismiss()
        }
    }

    private fun bindObserver() {

        mainViewModel.deleteLiveData.observe(viewLifecycleOwner, Observer{
            binding.transparentBg.isVisible = false
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(activity as MainActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                    tokenManager.saveToken("")
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    (activity as MainActivity).finish()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity as MainActivity, it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.transparentBg.isVisible = true
                    binding.progressBar.isVisible = true
                }

                else -> {}
            }
        })


        mainViewModel.updateBioLiveData.observe(viewLifecycleOwner, Observer{
            binding.progressBar2.isVisible = false

            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(activity as MainActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity as MainActivity, it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar2.isVisible = true
                }

                else -> {}
            }

        })

        mainViewModel.getUserDetailsLiveData.observe(viewLifecycleOwner, Observer {
            binding.transparentBg.isVisible = false
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    binding.name.text = response.name
                    binding.branch.text = response.branch
                    when (response.year) {
                        1 -> {
                            binding.year.text = "1st Year"
                        }
                        2 -> {
                            binding.year.text = "2nd Year"
                        }
                        3 -> {
                            binding.year.text = "3rd Year"
                        }
                        else -> {
                            binding.year.text = "4th Year"
                        }
                    }


                    if(it.data.bio == null){
                        binding.editBio.setText("Hello EveryOne!")
                    }else{
                        binding.editBio.setText(it.data.bio.toString())
                    }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity as MainActivity, it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.transparentBg.isVisible = true
                    binding.progressBar.isVisible = true
                }

                else -> {}
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}