package com.example.countlories.data.api

import com.example.countlories.data.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("forums")
    fun postForum(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody
    ): Call<UploadForumResponse>

    @GET("forums")
    fun getForums() : Call<GetAllForumResponse>

    @GET("forums/{id}")
    fun getDetailForum(
        @Path("id") id: String
    ): Call<GetDetailForumResponse>

    @FormUrlEncoded
    @POST("forums/{id}/comments")
    fun postComment(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("comment") comment: String
    ): Call<PostCommentResponse>

    @GET("blogs")
    fun getBlogs() : Call<GetAllBlogResponse>

    @GET("blogs/{id}")
    fun getDetailBlog(
        @Path("id") id: String
    ): Call<GetDetailBlogResponse>

    @Multipart
    @POST("predict")
    fun predictImage(
        @Part image: MultipartBody.Part
    ): Call<PredictResponse>

    @GET("menus/{id}")
    fun getDetailMenu(
        @Path("id") id: String
    ): Call<DetailMenu>

    @GET("menus")
    fun getAllMenu(): Call<GetAllMenu>

}