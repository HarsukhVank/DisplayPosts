package com.hv.displayposts.apis

import com.hv.displayposts.models.PostsModel
import retrofit2.Call

class APIClient constructor(private val apiService: APIService){
        fun getPosts() : Call<List<PostsModel>> = apiService.getPosts()
        fun getUserDetails(id: Int) = apiService.getUserDetails(id)
}
