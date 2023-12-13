package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: User? = null
)

data class User (
    @field: SerializedName("userId")
    val userId: String,
    @field: SerializedName("name")
    val name: String,
    @field: SerializedName("email")
    val email: String,
    @field: SerializedName("authentication_token")
    val authentication_token: String
)
