package com.example.countlories.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.countlories.databinding.ActivitySpalshScreenBinding
import com.example.countlories.ui.authentication.RegisterActivity
import com.example.countlories.ui.boarding.BoardingActivity
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.ui.welcome.WelcomeActivity
import com.example.countlories.utils.Constant
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.main.MainViewModel

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshScreenBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewSetUp()
        animationSetUp()
    }

    private fun viewSetUp(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)



        mainViewModel.getUserData().observe(this){user ->

            sharedPreferences = getSharedPreferences("boarding", Context.MODE_PRIVATE)
            val isViewed = sharedPreferences.getBoolean("viewed", false)
            val editor = sharedPreferences.edit()

            if (user.isLogin){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else if (isViewed) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, BoardingActivity::class.java))
                editor.putBoolean("viewed", true)
                editor.apply()
                finish()
            }
        }
    }

    private fun animationSetUp() {
        binding.imageView.alpha = 0f
        binding.imageView.animate().setDuration(Constant.SPLASH_DURATION).alpha(1f).withEndAction{
            setupViewModel()
        }
    }
}