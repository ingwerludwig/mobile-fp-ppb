package com.example.countlories.ui.boarding

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.countlories.R
import com.example.countlories.databinding.ActivityBoardingSecondBinding
import com.example.countlories.databinding.ActivityLoginBinding
import com.example.countlories.ui.welcome.WelcomeActivity

class BoardingSecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardingSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("anim", "boarding 2")
        binding = ActivityBoardingSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupAction(){
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, BoardingActivity::class.java))
            transition()
        }

        binding.continueButton.setOnClickListener {
            startActivity(Intent(this, BoardingThirdActivity::class.java))
            transition()
        }

        binding.skipButton.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            transition()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        transition()
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }
}