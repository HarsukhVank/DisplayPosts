package com.hv.displayposts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.hv.displayposts.R
import com.hv.displayposts.databinding.PostDetailsActivityBinding
import com.hv.displayposts.utility.Constants

class PostDetailsActivity : AppCompatActivity() {
    private lateinit var binding: PostDetailsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.post_details_activity)
        setIntentValue()
    }

    private fun setIntentValue() {
        intent?.let{
            val title = intent.getStringExtra(Constants.KEY_TITLE)
            val imageUrl =  "https://picsum.photos/seed/sha256($title)/100/100"
            Glide.with(this).load(imageUrl).centerCrop().into(binding.imageView)
            binding.title.text = title
            binding.body.text = intent.getStringExtra(Constants.KEY_BODY)
            binding.authorName.text = intent.getStringExtra(Constants.KEY_NAME)
            binding.email.text = intent.getStringExtra(Constants.KEY_EMAIL)
            binding.website.text = intent.getStringExtra(Constants.KEY_WEBSITE)
            binding.phone.text = intent.getStringExtra(Constants.KEY_PHONE)
            binding.userName.text = intent.getStringExtra(Constants.KEY_USER_NAME)
        }
    }
}