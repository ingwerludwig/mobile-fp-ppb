package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName

data class GetAllForumResponse(
    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: ArrayList<Forum>
)

data class Forum(
    @field: SerializedName("id")
    val id: String,
    @field: SerializedName("title")
    val title: String,
    @field: SerializedName("description")
    val description: String,
    @field: SerializedName("image")
    val image: String,
    @field: SerializedName("createdAt")
    val createdAt: String,
    @field: SerializedName("CommentCount")
    val CommentCount: Int
)