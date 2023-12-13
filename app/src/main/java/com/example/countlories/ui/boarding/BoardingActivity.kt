package com.example.countlories.ui.boarding

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.countlories.R
import com.example.countlories.databinding.ActivityBoardingBinding
import com.example.countlories.ui.welcome.WelcomeActivity

class BoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingBinding.inflate(layoutInflater)
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
        binding.continueButton.setOnClickListener {
            startActivity(Intent(this, BoardingSecondActivity::class.java))
            this@BoardingActivity.transition()
            finish()
        }
        binding.skipButton.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            this@BoardingActivity.transition()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        this.transition()
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

}