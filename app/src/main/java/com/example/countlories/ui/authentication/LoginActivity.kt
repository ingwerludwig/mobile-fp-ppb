package com.example.countlories.ui.authentication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.countlories.R
import com.example.countlories.data.model.UserModel
import com.example.countlories.databinding.ActivityLoginBinding
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.authentication.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        loginViewModel.loginData.observe(this){user ->
            if (user.status){
                saveSession(
                    UserModel(
                        user.data?.name.toString(),
                        user.data?.authentication_token.toString(),
                        user.data?.email.toString(),
                        true
                    )
                )
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                this.transition()
            }
        }

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }

        loginViewModel.isError.observe(this){
            showError(it)
        }

    }

    private fun setupAction(){
        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isEmailValid(email) && isPasswordValid(password)){
                loginViewModel.postLogin(email, password)
            } else {
                Toast.makeText(this, "Format email atau password tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSession(user: UserModel){
        loginViewModel.saveUser(user)
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
            Toast.makeText(this, "Password or Email didn't matched with criteria", Toast.LENGTH_SHORT).show()
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