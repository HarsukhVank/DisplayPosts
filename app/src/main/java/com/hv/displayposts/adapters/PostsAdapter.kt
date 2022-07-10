package com.hv.displayposts.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hv.displayposts.activity.PostDetailsActivity
import com.hv.displayposts.apis.APIClient
import com.hv.displayposts.apis.APIService
import com.hv.displayposts.databinding.PostViewBinding
import com.hv.displayposts.models.PostsModel
import com.hv.displayposts.models.UserModel
import com.hv.displayposts.utility.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsAdapter : RecyclerView.Adapter<MainViewHolder>() {
    private var postsList = mutableListOf<PostsModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPostsListInAdapter(postsList: List<PostsModel>) {
        this.postsList = postsList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostViewBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val context = holder.itemView.context
        val title = postsList[position].title
        val body = postsList[position].body
        holder.binding.title.text = title
        holder.binding.body.text = body
        displayAuthor(holder, postsList[position].id, context, title, body)
        displayImage(holder, title, context)
    }

    private fun displayImage(holder: MainViewHolder, title: String, context: Context) {
        val imageUrl =  "https://picsum.photos/seed/sha256($title)/100/100"
        Glide.with(context).load(imageUrl).centerCrop().into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    private fun displayAuthor(holder: MainViewHolder, id: Int, context: Context, title: String, body: String) {
        APIClient(APIService.getInstance()).getUserDetails(id)
            .enqueue(object : Callback<UserModel> {
                override fun onResponse(
                    call: Call<UserModel>,
                    response: Response<UserModel>
                ) {
                    Log.d(TAG, "User details API call success: $id ")
                    val userModel = response.body()
                    userModel?.let {
                        userModel.name.let {
                            holder.binding.authorName.text = userModel.name
                        }
                        holder.itemView.setOnClickListener {
                            val intent = Intent(context, PostDetailsActivity::class.java)
                            val bundle = Bundle()
                            bundle.putString(Constants.KEY_TITLE, title)
                            bundle.putString(Constants.KEY_BODY, body)
                            bundle.putString(Constants.KEY_NAME, userModel.name)
                            bundle.putString(Constants.KEY_EMAIL, userModel.email)
                            bundle.putString(Constants.KEY_WEBSITE, userModel.website)
                            bundle.putString(Constants.KEY_PHONE, userModel.phone)
                            bundle.putString(Constants.KEY_USER_NAME, userModel.username)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.d(TAG, "User details API call failed: $id")
                }
            })
    }

    companion object {
        private const val TAG = "PostsAdapter"
    }
}

class MainViewHolder(val binding: PostViewBinding) : RecyclerView.ViewHolder(binding.root)
