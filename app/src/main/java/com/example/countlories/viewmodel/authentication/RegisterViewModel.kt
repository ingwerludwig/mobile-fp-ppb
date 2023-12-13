package com.example.countlories.viewmodel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.countlories.data.response.RegisterResponse
import com.example.countlories.repository.Repository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: Repository) : ViewModel(){

    val registerData : LiveData<RegisterResponse> = repository.registerData
    val isLoading : LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError

    fun postRegister(name: String, email: String, password:String){
        viewModelScope.launch {
            repository.postRegister(name, email, password)
        }
    }
}