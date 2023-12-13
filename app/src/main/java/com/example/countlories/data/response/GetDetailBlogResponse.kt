package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName

data class GetDetailBlogResponse(

    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: DetailBlog

)

data class DetailBlog(
    @field: SerializedName("id")
    val id: String,
    @field: SerializedName("title")
    val title: String,
    @field: SerializedName("content")
    val content: String,
    @field: SerializedName("image")
    val image: String
)



