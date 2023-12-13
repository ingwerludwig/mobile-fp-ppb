package com.example.countlories.ui.camera

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.countlories.R
import com.example.countlories.data.response.PredictResponse
import com.example.countlories.databinding.ActivityPreviewErrorBinding
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.predict.PredictViewModel

class PreviewErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupData()
        setupAction()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupData() {
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        binding.titleExplain.text = getString(R.string.sorry_msg, message)
    }



    private fun setupAction(){
        binding.takeButton.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            transition()
            finish()
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            transition()
            finish()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
    }

}