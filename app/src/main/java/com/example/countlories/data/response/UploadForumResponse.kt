package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName

data class UploadForumResponse(
    @field: SerializedName("status")
    val status: Boolean,

    @field: SerializedName("code")
    val code: Int,

    @field: SerializedName("message")
    val message: String,

    @field: SerializedName("data")
    val data: ForumResponse? = null
)

data class ForumResponse(
    @field: SerializedName("id")
    val id: Int,

    @field: SerializedName("title")
    val title: String,

    @field: SerializedName("description")
    val description: String,

    @field: SerializedName("image")
    val image: String
)
