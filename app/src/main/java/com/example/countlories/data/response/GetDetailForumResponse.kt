package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName


data class GetDetailForumResponse(

    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: DetailForum

)

data class DetailForum(
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
    @field: SerializedName("ForumComments")
    val forumComments: ArrayList<Comments>? = null
)

data class Comments(
    @field: SerializedName("id")
    val id: String,
    @field: SerializedName("comment")
    val comment: String,
    @field: SerializedName("createdAt")
    val createdAt: String,
    @field: SerializedName("User")
    val user: Name
)

data class Name(
    @field: SerializedName("name")
    val name: String
)