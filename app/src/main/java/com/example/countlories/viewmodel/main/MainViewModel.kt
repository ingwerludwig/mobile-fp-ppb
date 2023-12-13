package com.example.countlories.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countlories.data.model.UserModel
import com.example.countlories.data.response.GetAllBlogResponse
import com.example.countlories.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: Repository) : ViewModel() {

    val allBlogs : LiveData<GetAllBlogResponse> = repository.allBlogs
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getAllBlogs(){
        viewModelScope.launch {
            repository.getAllBlogs()
        }
    }

    fun getUserData() : LiveData<UserModel> {
        return repository.getUser()
    }

}