package com.example.countlories.ui.authentication

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.countlories.R
import com.example.countlories.databinding.ActivityRegisterBinding
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.authentication.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)

        registerViewModel.registerData.observe(this){user ->
            if (user.status){
                AlertDialog.Builder(this).apply {
                    setTitle("Akun berhasil dibuat!")
                    setMessage("Silahkan login terlebih dahulu")
                    setPositiveButton("Lanjut") {_, _ ->
                        finish()
                        this@RegisterActivity.transition()
                    }
                    create()
                    show()
                }
            }
        }

        registerViewModel.isLoading.observe(this){
            showLoading(it)
        }

        registerViewModel.isError.observe(this){
            showError(it)
        }

    }

    private fun setupAction(){
        binding.registerButton.setOnClickListener {

            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isEmailValid(email) && isPasswordValid(password)){
                registerViewModel.postRegister(name, email, password)
             }else if(name.isEmpty() && email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "Pastikan data anda sudah terisi semua", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Format email atau password tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValid(email: String) : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String) : Boolean{
        val pattern = Regex("^(?=.*[A-Z]).{8,}$")
        return pattern.matches(password)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean){
        if (isError){
            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.transition()
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }


}