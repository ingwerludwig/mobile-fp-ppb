package com.example.countlories.viewmodel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countlories.data.model.UserModel
import com.example.countlories.data.response.LoginResponse
import com.example.countlories.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: Repository) : ViewModel() {

    val loginData : LiveData<LoginResponse> = repository.loginData
    val isLoading: LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError

    fun postLogin(email: String, password: String){
        viewModelScope.launch {
            repository.postLogin(email, password)
        }
    }

    fun saveUser(user: UserModel){
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }
}