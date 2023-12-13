package com.example.countlories.ui.community

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.countlories.R
import com.example.countlories.adapter.CommentAdapter
import com.example.countlories.data.response.Comments
import com.example.countlories.data.response.DetailForum
import com.example.countlories.data.response.Forum
import com.example.countlories.data.response.GetDetailForumResponse
import com.example.countlories.databinding.ActivityDetailForumBinding
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.forum.ForumViewModel

class DetailForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailForumBinding

    private lateinit var factory: ViewModelFactory
    private val forumViewModel: ForumViewModel by viewModels { factory }

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CommunityActivity::class.java))
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

        id = intent.getStringExtra(EXTRA_DATA) ?: ""

        forumViewModel.getDetailForum(id as String)

        forumViewModel.detailForum.observe(this){ forum ->
            setupData(forum)
        }

    }

    private fun setupData(data: GetDetailForumResponse){
        setupDetailData(data.data)
    }

    private fun setupDetailData(forumList: DetailForum){

        binding.apply {
            tvTitle.text = forumList.title
            tvDescItem.text = forumList.description
            Glide.with(ivStory.context)
                .load(forumList.image)
                .into(ivStory)
            forumList.forumComments?.let { setupAdapter(it) }
        }
    }

    private fun setupAdapter(comment: ArrayList<Comments>){
        binding.rvComment.layoutManager = LinearLayoutManager(this)
        val adapter = CommentAdapter(comment)
        binding.rvComment.adapter = adapter
    }

    private fun setupAction(){
        Log.d("CHECK ID: ", id.toString())

        binding.btnPost.setOnClickListener {
            val comment = binding.edtTitleStory.text.toString()
            if (comment.isEmpty()){
                Toast.makeText(this, "Pastikan komentar anda sudah diisi", Toast.LENGTH_SHORT).show()
            } else {
                forumViewModel.getUserData().observe(this){ user ->

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

                    forumViewModel.postComment(
                        user.token,
                        id.toString(),
                        comment
                    )

                    Toast.makeText(this, "Comment Success", Toast.LENGTH_SHORT).show()
                    recreate()
                    finish()
                    startActivity(Intent(this, DetailForumActivity::class.java))

                }
            }
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}