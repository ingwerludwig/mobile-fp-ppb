package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(
    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: PostComment? = null
)

data class PostComment(
    @field: SerializedName("id")
    val id: String,
    @field: SerializedName("comment")
    val comment: String
)
