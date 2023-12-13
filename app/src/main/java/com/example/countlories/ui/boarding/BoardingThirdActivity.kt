package com.example.countlories.ui.boarding

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.example.countlories.R
import com.example.countlories.databinding.ActivityBoardingThirdBinding
import com.example.countlories.ui.welcome.WelcomeActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.main.MainViewModel

class BoardingThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardingThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingThirdBinding.inflate(layoutInflater)
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
            startActivity(Intent(this, WelcomeActivity::class.java))
            transition()
            finish()
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, BoardingSecondActivity::class.java))
            transition()
            finish()
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