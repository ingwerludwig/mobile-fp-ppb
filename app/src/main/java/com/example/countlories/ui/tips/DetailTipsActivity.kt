package com.example.countlories.ui.tips

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.countlories.R
import com.example.countlories.data.response.DetailBlog
import com.example.countlories.data.response.GetDetailBlogResponse
import com.example.countlories.databinding.ActivityDetailTipsBinding
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.tips.TipsViewModel
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist

class DetailTipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTipsBinding

    private lateinit var factory: ViewModelFactory
    private val tipsViewModel: TipsViewModel by viewModels { factory }

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, TipsActivity::class.java))
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

        id = intent.getStringExtra(EXTRA_ID) ?: ""

        tipsViewModel.getDetailBlog(id as String)

        tipsViewModel.detailBlog.observe(this){ story ->
            setupData(story)
        }
    }

    private fun setupData(data: GetDetailBlogResponse){
        setupDetailData(data.data)
    }

    private fun setupDetailData(blog: DetailBlog){
        @Suppress("DEPRECATION")
        val descriptionHTML = blog.content
        val cleanedDescription = Jsoup.clean(descriptionHTML, "", Whitelist.none(), org.jsoup.nodes.Document.OutputSettings().prettyPrint(false))


        binding.apply {
            titleDetail.text = blog.title
            contentDetail.text = cleanedDescription
            Glide.with(detailImg.context)
                .load(blog.image)
                .into(detailImg)
        }
    }

    private fun setupAction(){
        binding.backButton.setOnClickListener {
            onBackPressed()
            transition()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

    companion object{
        const val EXTRA_ID = "extra_data"
    }
}