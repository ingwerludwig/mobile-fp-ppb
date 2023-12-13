package com.example.countlories.ui.tips

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countlories.R
import com.example.countlories.adapter.BlogAdapter
import com.example.countlories.data.response.Blog
import com.example.countlories.data.response.GetAllBlogResponse
import com.example.countlories.databinding.ActivityTipsBinding
import com.example.countlories.ui.account.AccountActivity
import com.example.countlories.ui.camera.CameraActivity
import com.example.countlories.ui.community.CommunityActivity
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.tips.TipsViewModel

class TipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTipsBinding

    private lateinit var factory: ViewModelFactory
    private val tipsViewModel: TipsViewModel by viewModels { factory }

    private lateinit var blogAdapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
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

        tipsViewModel.getAllBlogs()

        tipsViewModel.allBlogs.observe(this){tips ->
            setupData(tips)
        }
    }

    private fun setupData(data: GetAllBlogResponse){
        setupAdapter(data.data)
    }

    private fun setupAdapter(tips: ArrayList<Blog>){

        binding.rvTips.layoutManager = LinearLayoutManager(this)
        val adapter = BlogAdapter(tips)
        binding.rvTips.adapter = adapter

        adapter.setOnItemClickCallback(object : BlogAdapter.OnItemClickCallback{
            override fun onItemClicked(blog: Blog) {
                toDetailActivity(blog)
            }
        })
    }

    private fun toDetailActivity(blog: Blog){
        val intent = Intent(this@TipsActivity, DetailTipsActivity::class.java)
        intent.putExtra(DetailTipsActivity.EXTRA_ID, blog.id)
        startActivity(intent)
        finish()
    }

    private fun setupAction(){
        bottomNavigation()
    }

    private fun bottomNavigation(){
        binding.homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            transition()
            finish()
        }

        binding.forumBtn.setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
            transition()
            finish()
        }

        binding.accountBtn.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
            transition()
            finish()
        }

        binding.scanBtn.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            transition()
            finish()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }
}