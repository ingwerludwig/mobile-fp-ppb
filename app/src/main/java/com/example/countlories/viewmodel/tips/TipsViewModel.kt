package com.example.countlories.viewmodel.tips

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countlories.data.model.UserModel
import com.example.countlories.data.response.GetAllBlogResponse
import com.example.countlories.data.response.GetDetailBlogResponse
import com.example.countlories.repository.Repository
import kotlinx.coroutines.launch

class TipsViewModel (private val repository: Repository) : ViewModel() {

    val allBlogs : LiveData<GetAllBlogResponse> = repository.allBlogs
    val detailBlog: LiveData<GetDetailBlogResponse> = repository.detailBlog
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getAllBlogs(){
        viewModelScope.launch {
            repository.getAllBlogs()
        }
    }

    fun getDetailBlog(id: String){
        viewModelScope.launch {
            repository.getDetailBlog(id)
        }
    }
}