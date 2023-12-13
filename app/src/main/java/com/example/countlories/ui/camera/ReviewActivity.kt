package com.example.countlories.ui.camera

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.countlories.R
import com.example.countlories.databinding.ActivityReviewBinding
import com.example.countlories.ui.menu.MenuActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.predict.PredictViewModel


class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    private lateinit var factory: ViewModelFactory
    private val predictViewModel: PredictViewModel by viewModels { factory }

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CameraActivity::class.java))
        finish()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)

        predictViewModel.predictData.observe(this) { result ->

            Log.d("MENU SAAT INI: ", "$result")

            showUI()

            if (result.message == "Success") {

                id = result.data.id

                binding.titleTextView.text = result.data.name
                binding.calories.text = getString(R.string.calories_ex, result.data.calories)

                Glide.with(binding.cardImageProfile.context)
                    .load(result.data.image)
                    .into(binding.cardImageProfile)

            } else{
                val intent = Intent(this, PreviewErrorActivity::class.java)

                intent.putExtra(PreviewErrorActivity.EXTRA_MESSAGE, result.message)
                startActivity(intent)
                transition()
                finish()
            }
        }
    }

    private fun setupAction(){

        binding.takeButton.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            transition()
            finish()
        }

        binding.detailButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(MenuActivity.EXTRA_ID, id)
            startActivity(intent)
            finish()
        }
    }

    private fun showUI(){
        @Suppress("DEPRECATION")
        val handler= Handler()
        binding.progressBar.visibility = View.VISIBLE

        handler.postDelayed({
            binding.cardImageProfile.visibility = View.VISIBLE
            binding.contentLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }, 3000)
    }



    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

}