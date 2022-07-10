package com.hv.displayposts.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hv.displayposts.apis.APIClient
import com.hv.displayposts.models.PostsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayPostsViewModel constructor(private val apiClient: APIClient) : ViewModel() {
    private val TAG = "DisplayPostsViewModel"
    val postsList = MutableLiveData<List<PostsModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getPosts() {
        apiClient.getPosts().enqueue(object : Callback<List<PostsModel>> {
            override fun onResponse(
                call: Call<List<PostsModel>>,
                response: Response<List<PostsModel>>
            ) {
                Log.d(TAG, "onResponse")
                if (response.body() != null) {
                    postsList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<PostsModel>>, t: Throwable) {
                Log.d(TAG, "onFailure")
                errorMessage.postValue(t.message)
            }
        })
    }
}
