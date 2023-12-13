package com.example.countlories.ui.account

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.countlories.R
import com.example.countlories.databinding.ActivityAccountBinding
import com.example.countlories.ui.camera.CameraActivity
import com.example.countlories.ui.community.CommunityActivity
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.ui.tips.TipsActivity
import com.example.countlories.ui.welcome.WelcomeActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.profile.ProfileViewModel

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        transition()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)

        profileViewModel.getUserData().observe(this){ user ->
            binding.welcomeMsg.text = getString(R.string.greeting, user.name)
            binding.emailUser.text = user.email
        }
    }

    private fun setupAction(){
        bottomNavigation()
        logoutButton()
    }

    private fun bottomNavigation(){
        binding.homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            transition()
        }

        binding.forumBtn.setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
            transition()
        }

        binding.tipsBtn.setOnClickListener {
            startActivity(Intent(this, TipsActivity::class.java))
            transition()
        }

        binding.scanBtn.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            transition()
        }
    }

    private fun logoutButton(){
        binding.signoutBtn.setOnClickListener {
            profileViewModel.logout()
            Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

}