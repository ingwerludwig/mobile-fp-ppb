package com.example.countlories.ui.history

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countlories.R
import com.example.countlories.adapter.MenuAdapter
import com.example.countlories.data.response.GetAllMenu
import com.example.countlories.data.response.Menu
import com.example.countlories.databinding.ActivityHistoryBinding
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.ui.menu.MenuActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.predict.PredictViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var factory: ViewModelFactory
    private val predictViewModel: PredictViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()

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

        predictViewModel.getAllMenu()

        predictViewModel.getAllMenuData.observe(this){ menu ->
            setupData(menu)
        }
    }

    private fun setupData(data: GetAllMenu){
        setupAdapter(data.data)
    }

    private fun setupAdapter(menu: ArrayList<Menu>){
        binding.rvTips.layoutManager = LinearLayoutManager(this)
        val adapter = MenuAdapter(menu)
        binding.rvTips.adapter = adapter

        adapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback {
            override fun onItemClicked(menu: Menu) {
                toDetailActivity(menu)
            }
        }
    )}

    private fun toDetailActivity(menu: Menu){
        val intent = Intent(this@HistoryActivity, MenuActivity::class.java)
        intent.putExtra(MenuActivity.EXTRA_ID, menu.id)
        startActivity(intent)
        transition()
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }
}