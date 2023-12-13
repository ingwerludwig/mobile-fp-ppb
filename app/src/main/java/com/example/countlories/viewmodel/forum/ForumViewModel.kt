package com.example.countlories.viewmodel.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countlories.data.model.UserModel
import com.example.countlories.data.response.GetAllForumResponse
import com.example.countlories.data.response.GetDetailForumResponse
import com.example.countlories.data.response.PostCommentResponse
import com.example.countlories.data.response.UploadForumResponse
import com.example.countlories.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ForumViewModel (private val repository: Repository) : ViewModel() {

    val postForumData : LiveData<UploadForumResponse> = repository.postForumData
    val postCommentData : LiveData<PostCommentResponse> = repository.postCommentData
    val detailForum: LiveData<GetDetailForumResponse> = repository.detailForum
    val allForums : LiveData<GetAllForumResponse> = repository.allForums
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun postForum(token: String, image: MultipartBody.Part, title: RequestBody, description: RequestBody){
        viewModelScope.launch {
            repository.postForum(token, image, title, description)
        }
    }

    fun postComment(token: String, id: String, comment: String){
        viewModelScope.launch {
            repository.postComment(token, id, comment)
        }
    }

    fun getAllForum(){
        viewModelScope.launch {
            repository.getAllForum()
        }
    }

    fun getDetailForum(id: String){
        viewModelScope.launch {
            repository.getDetailForum(id)
        }
    }

    fun getUserData() : LiveData<UserModel> {
        return repository.getUser()
    }

}