package com.example.countlories.ui.community

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countlories.R
import com.example.countlories.adapter.ForumAdapter
import com.example.countlories.data.response.Forum
import com.example.countlories.data.response.GetAllForumResponse
import com.example.countlories.databinding.ActivityCommunityBinding
import com.example.countlories.ui.account.AccountActivity
import com.example.countlories.ui.camera.CameraActivity
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.ui.tips.TipsActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.forum.ForumViewModel

class CommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityBinding

    private lateinit var factory: ViewModelFactory
    private val forumViewModel: ForumViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
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

        forumViewModel.getAllForum()

        forumViewModel.allForums.observe(this){forum ->
            setupData(forum)
        }
    }

    private fun setupAction(){
        bottomNavigation()
    }

    private fun setupData(data: GetAllForumResponse){
        setupAdapter(data.data)
    }

    private fun setupAdapter(forum: ArrayList<Forum>){
        binding.rvForum.layoutManager = LinearLayoutManager(this)
        val adapter = ForumAdapter(forum)
        binding.rvForum.adapter = adapter

        adapter.setOnItemClickCallback(object : ForumAdapter.OnItemClickCallback{
            override fun onItemClicked(forum: Forum) {
                toDetailActivity(forum)
            }
        })
    }

    private fun toDetailActivity(forum: Forum){
        val intent = Intent(this@CommunityActivity, DetailForumActivity::class.java)
        intent.putExtra(DetailForumActivity.EXTRA_DATA, forum.id)
        startActivity(intent)
        transition()
    }

    private fun bottomNavigation(){
        binding.tipsBtn.setOnClickListener {
            startActivity(Intent(this, TipsActivity::class.java))
            transition()
        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, UploadForumActivity::class.java))
            transition()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }
}