package com.example.flatmatefinder

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.api.MainAPI
import com.example.flatmatefinder.models.FlatInfoRequest1
import com.example.flatmatefinder.models.UserDetailsResponse
import com.example.flatmatefinder.viewModels.MainViewModel
import com.example.flatmatefinder.viewModels.OnboardingViewModel
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val onboardingViewModel by viewModels<OnboardingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        mainViewModel.getUserDetails()


        val flatYes = findViewById<AppCompatButton>(R.id.flat_yes)
        val flatNo = findViewById<AppCompatButton>(R.id.flat_no)
        val flatInfo = findViewById<ConstraintLayout>(R.id.flatInfoLayout)
        val branchSpinner = findViewById<Spinner>(R.id.branchSpinner)
        val yearSpinner = findViewById<Spinner>(R.id.yearSpinner)

        flatNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_no_active))
        flatNo.setTextColor(Color.parseColor("#FFFFFF"))

        ArrayAdapter.createFromResource(
            this, R.array.branch,
            R.layout.spinner_style
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            branchSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.year,
            R.layout.spinner_style
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.adapter = adapter
        }


        flatYes.setOnClickListener {
            flatYes.setTextColor(Color.parseColor("#FFFFFF"))
            flatNo.setTextColor(Color.parseColor("#D63835"))
            flatYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_yes_active))
            flatNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_no))

            flatInfo.visibility = View.VISIBLE
        }

        flatNo.setOnClickListener {
            flatYes.setTextColor(Color.parseColor("#39DD00"))
            flatNo.setTextColor(Color.parseColor("#FFFFFF"))
            flatYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_yes))
            flatNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_no_active))

            flatInfo.visibility = View.GONE
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }


        findViewById<AppCompatButton>(R.id.ok).setOnClickListener {
            val c = findViewById<EditText>(R.id.input_capacity).text.toString().toInt()
            val slider = findViewById<Slider>(R.id.occupiedSlider)
            if (c < 1) {
                Toast.makeText(
                    this, "Enter value greater than equal to 1",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                slider.value = 0F
                slider.valueFrom = 0F

                slider.valueTo = c.toFloat()
            }

        }

        bindObserver()
    }

    private fun bindObserver() {
        mainViewModel.getUserDetailsLiveData.observe(this, Observer {
            findViewById<View>(R.id.transparentBg).isVisible = false
            findViewById<ProgressBar>(R.id.progressBar).isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    updateUI(it)
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    findViewById<View>(R.id.transparentBg).isVisible = true
                    findViewById<ProgressBar>(R.id.progressBar).isVisible = true
                }
            }
        })
    }

    private fun updateUI(it: NetworkResult.Success<UserDetailsResponse>) {
        val response = it.data!!
        val name = findViewById<EditText>(R.id.name)
        val branchSpinner = findViewById<Spinner>(R.id.branchSpinner)
        val yearSpinner = findViewById<Spinner>(R.id.yearSpinner)
        val flatYes = findViewById<AppCompatButton>(R.id.flat_yes)
        val flatNo = findViewById<AppCompatButton>(R.id.flat_no)
        val inputFlatNo = findViewById<EditText>(R.id.input_flat_no)
        val inputArea = findViewById<EditText>(R.id.input_area)
        val inputAddInfo = findViewById<EditText>(R.id.input_addInfo)
        val inputMonthlyAmount = findViewById<EditText>(R.id.input_monthlyAmount)
        val inputBrokerage = findViewById<EditText>(R.id.input_brokerageAmount)
        val furnishStatus = findViewById<RadioGroup>(R.id.radioInfo)
        val bhk = findViewById<EditText>(R.id.input_size)
        val capacity = findViewById<EditText>(R.id.input_capacity)
        val ok = findViewById<AppCompatButton>(R.id.ok)
        val slider = findViewById<Slider>(R.id.occupiedSlider)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.flatInfoLayout)

        name.setText(response.name)
        when (response.branch) {
            "MBA" -> {
                branchSpinner.setSelection(0)
            }

            "MCA" -> {
                branchSpinner.setSelection(1)
            }

            "B. Pharma" -> {
                branchSpinner.setSelection(2)

            }

            "Btech (CSE)" -> {

                branchSpinner.setSelection(3)
            }

            "Btech (CSE-AIML)" -> {
                branchSpinner.setSelection(4)

            }

            "Btech (CSE-DS)" -> {
                branchSpinner.setSelection(5)

            }

            "Btech (IT)" -> {
                branchSpinner.setSelection(6)

            }

            "Btech (ECE)" -> {
                branchSpinner.setSelection(7)

            }

            "Btech (EEE)" -> {
                branchSpinner.setSelection(8)

            }

            "Btech (EE)" -> {
                branchSpinner.setSelection(9)

            }

            "Btech (Mechanical)" -> {
                branchSpinner.setSelection(10)
            }

            "Btech (Civil)" -> {
                branchSpinner.setSelection(11)

            }
        }

        yearSpinner.setSelection(response.year - 1)

        if (response.hasFlat) {
            flatYes.setTextColor(Color.parseColor("#FFFFFF"))
            flatNo.setTextColor(Color.parseColor("#D63835"))
            flatYes.setBackgroundDrawable(resources.getDrawable(R.drawable.button_yes_active))
            flatNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_no))

            constraintLayout.isVisible = true


            inputFlatNo.setText(response.address.flat)
            inputArea.setText(response.address.area)
            inputAddInfo.setText(response.address.additional)
            inputMonthlyAmount.setText(response.rent.monthlyAmount.toString())
            inputBrokerage.setText(response.rent.brokerage.toString())
            when (response.furnishingStatus) {
                "fullyFurnished" -> furnishStatus.check(R.id.fullyFurnished)
                "semiFurnished" -> furnishStatus.check(R.id.semiFurnished)
                "nonFurnished" -> furnishStatus.check(R.id.nonFurnished)
                else -> {}
            }
            capacity.setText(response.capacity.toString())
            slider.valueFrom = 0F
            slider.valueTo = response.capacity.toFloat()
            slider.value = response.occupied.toFloat()
            bhk.setText(response.bhk.toString())

        }else {
            flatNo.setTextColor(Color.parseColor("#FFFFFF"))
            flatNo.setBackgroundDrawable(resources.getDrawable(R.drawable.button_no_active))
            constraintLayout.isVisible = false
        }
    }


}

