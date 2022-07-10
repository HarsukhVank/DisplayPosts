package com.hv.displayposts.apis

import com.hv.displayposts.models.PostsModel
import com.hv.displayposts.models.UserModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("/posts")
    fun getPosts(): Call<List<PostsModel>>

    @GET("/users/{id}")
    fun getUserDetails(@Path("id") id: Int): Call<UserModel>

    companion object {
        fun getInstance(): APIService {
            return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}