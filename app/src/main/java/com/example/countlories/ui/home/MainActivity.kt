package com.example.countlories.ui.home

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.countlories.R
import com.example.countlories.data.response.Blog
import com.example.countlories.data.response.GetAllBlogResponse
import com.example.countlories.databinding.ActivityMainBinding
import com.example.countlories.ui.account.AccountActivity
import com.example.countlories.ui.camera.CameraActivity
import com.example.countlories.ui.community.CommunityActivity
import com.example.countlories.ui.history.HistoryActivity
import com.example.countlories.ui.ideal_calculator.CalculatorActivity
import com.example.countlories.ui.tips.DetailTipsActivity
import com.example.countlories.ui.tips.TipsActivity
import com.example.countlories.utils.Constant
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)

        mainViewModel.getAllBlogs()

        mainViewModel.allBlogs.observe(this){ blog ->
            setupData(blog)
        }
    }

    private fun setupData(data: GetAllBlogResponse){
        newestTips(data.data)
    }

    private fun newestTips(blog: ArrayList<Blog>){
        val newestBlog : Blog = blog[0]
        binding.apply {
            tipsTitle.text = newestBlog.title
            Glide.with(tipsImg.context)
                .load(newestBlog.image)
                .into(tipsImg)
        }
    }

    private fun setupAction(){

        bottomNavigation()

        binding.cardItemBlog.setOnClickListener {
            mainViewModel.allBlogs.observe(this){ blog ->
                goToDetailTips(blog)
            }
        }
    }

    private fun goToDetailTips(data: GetAllBlogResponse){
        val intent = Intent(this, DetailTipsActivity::class.java)
        val id = data.data[0].id
        intent.putExtra(DetailTipsActivity.EXTRA_ID, id)
        startActivity(intent)
        onPause()
    }

    private fun bottomNavigation(){
        binding.tipsBtn.setOnClickListener {
            startActivity(Intent(this, TipsActivity::class.java))
            transition()
        }

        binding.forumBtn.setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
            transition()
        }

        binding.accountBtn.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
            transition()
        }

        binding.scanBtn.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            transition()
        }

        binding.idealBtn.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
            transition()
        }

        binding.foodBtn.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            transition()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }
}