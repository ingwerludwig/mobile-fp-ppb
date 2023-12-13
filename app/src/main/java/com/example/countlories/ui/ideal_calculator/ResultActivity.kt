package com.example.countlories.ui.ideal_calculator

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.countlories.R
import com.example.countlories.databinding.ActivityResultBinding
import com.example.countlories.viewmodel.calculator.CalculatorViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private var gender: String? = null
    private var weight: Double? = null
    private var height: Double? = null
    private var bmi: Double? = null
    private var ideal: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CalculatorActivity::class.java))
        transition()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setupCount()
        supportActionBar?.hide()
    }

    private fun setupCount(){
        gender = intent.getStringExtra(EXTRA_GENDER) ?: ""
        weight = intent.getDoubleExtra(EXTRA_WEIGHT, 0.0)
        height = intent.getDoubleExtra(EXTRA_HEIGHT, 0.0)
        bmi = intent.getDoubleExtra(EXTRA_BMI, 0.0)
        ideal = intent.getDoubleExtra(EXTRA_IDEAL, 0.0)

        val status = rangeBmi(bmi!!)

        binding.BMI.text = bmi.toString()
        binding.statusBmi.text = status

        val idealDesc = getString(R.string.ideal_desc, height.toString(), ideal.toString())
        binding.idealDesc.text = idealDesc


        Log.d("Result: ", "gender: $gender, berat: $weight, tinggi: $height, BMI: $bmi, Ideal: $ideal")

    }

    private fun setupAction(){
        binding.againButton.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
            transition()
        }
    }

    private fun rangeBmi(bmi: Double) : String{
        var status = ""
        val statusDesc = binding.statusDesc

        if (bmi < 18.5){
            status = "Underweight"
            statusDesc.setText(R.string.bmi_underweight)
        } else if (bmi in 18.5..24.9){
            status = "Normal"
            statusDesc.setText(R.string.bmi_normal)
        } else if (bmi in 25.0..29.9){
            status = "Overweight"
            statusDesc.setText(R.string.bmi_overweight)
        } else if (bmi in 30.0 .. 34.9){
            status = "Obesity class I"
            statusDesc.setText(R.string.bmi_obesity_1)
        } else if (bmi in 35.0 .. 39.9){
            status = "Obesity class II"
            statusDesc.setText(R.string.bmi_obesity_2)
        } else if (bmi >= 40.0){
            status = "Obesity class III"
            statusDesc.setText(R.string.bmi_obesity_3)
        }

        return status
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }


    companion object {
        const val EXTRA_GENDER = "extra_gender"
        const val EXTRA_WEIGHT = "extra_weight"
        const val EXTRA_HEIGHT = "extra_height"
        const val EXTRA_BMI = "extra_bmi"
        const val EXTRA_IDEAL = "extra_ideal"
    }
}