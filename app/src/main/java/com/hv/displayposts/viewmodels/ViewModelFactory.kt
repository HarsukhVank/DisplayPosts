package com.hv.displayposts.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hv.displayposts.apis.APIClient

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val apiClient: APIClient): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DisplayPostsViewModel::class.java)) {
            DisplayPostsViewModel(this.apiClient) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}