package com.example.countlories.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: Data? = null
)

data class Data(
    @field: SerializedName("name")
    val name: String,
    @field: SerializedName("email")
    val email: String,
    @field: SerializedName("password")
    val password: String
)
