package com.example.flatmatefinder

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout


class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val flatYes = findViewById<AppCompatButton>(R.id.flat_yes)
        val flatNo = findViewById<AppCompatButton>(R.id.flat_no)
        val flatInfo = findViewById<ConstraintLayout>(R.id.flatInfoLayout)
        val branchSpinner = findViewById<Spinner>(R.id.branchSpinner)
        val yearSpinner = findViewById<Spinner>(R.id.yearSpinner)

        ArrayAdapter.createFromResource(
            this, R.array.branch,
            R.layout.spinner_style
        ).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            branchSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.year,
            R.layout.spinner_style
        ).also {
                adapter ->
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

    }
}

