package com.example.countlories.data.response

import android.os.Message
import android.os.ParcelFileDescriptor
import com.google.gson.annotations.SerializedName

data class PredictResponse(

    @field: SerializedName("message")
    val message: String? = null,
    @field: SerializedName("data")
    val data: Menu
)

data class Menu(
    @field: SerializedName("name")
    val name: String,
    @field: SerializedName("kkal")
    val calories: String,
    @field: SerializedName("description")
    val description: String,
    @field: SerializedName("image")
    val image: String,
    @field: SerializedName("id")
    val id: String
)

data class DetailMenu(
    @field: SerializedName("status")
    val status: String,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: Menu? = null
)

data class GetAllMenu(
    @field: SerializedName("status")
    val status: Boolean,
    @field: SerializedName("code")
    val code: Int,
    @field: SerializedName("message")
    val message: String,
    @field: SerializedName("data")
    val data: ArrayList<Menu>
)