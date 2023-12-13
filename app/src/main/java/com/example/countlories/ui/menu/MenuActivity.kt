package com.example.countlories.ui.menu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.countlories.R
import com.example.countlories.data.response.DetailMenu
import com.example.countlories.data.response.Menu
import com.example.countlories.databinding.ActivityMenuBinding
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.predict.PredictViewModel
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    private lateinit var factory: ViewModelFactory
    private val predictViewModel: PredictViewModel by viewModels { factory }

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        transition()
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

        id = intent.getStringExtra(EXTRA_ID) ?: ""

        predictViewModel.getDetailMenu(id)

        predictViewModel.detailMenu.observe(this){menu ->
            setupMenu(menu)
        }
    }

    private fun setupMenu(data: DetailMenu){
        data.data?.let { setupData(it) }
    }

    private fun setupData(menu: Menu){
        @Suppress("DEPRECATION")
        val descriptionHTML = menu.description
        val cleanedDescription = Jsoup.clean(descriptionHTML, "", Whitelist.none(), org.jsoup.nodes.Document.OutputSettings().prettyPrint(false))

        binding.titleDetail.text = menu.name
        binding.caloriesDetail.text = getString(R.string.calories_ex, menu.calories)
        binding.contentDetail.text = cleanedDescription
        Glide.with(binding.detailImg.context)
            .load(menu.image)
            .into(binding.detailImg)
    }

    private fun setupAction(){
        binding.backButton.setOnClickListener {
            onBackPressed()
            transition()
            finish()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}