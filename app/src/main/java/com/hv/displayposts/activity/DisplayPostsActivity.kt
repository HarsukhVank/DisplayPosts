package com.hv.displayposts.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hv.displayposts.R
import com.hv.displayposts.adapters.PostsAdapter
import com.hv.displayposts.apis.APIClient
import com.hv.displayposts.apis.APIService
import com.hv.displayposts.databinding.DisplayPostsActivityBinding
import com.hv.displayposts.viewmodels.DisplayPostsViewModel
import com.hv.displayposts.viewmodels.ViewModelFactory


class DisplayPostsActivity : AppCompatActivity() {

    private lateinit var binding: DisplayPostsActivityBinding
    private lateinit var viewModel: DisplayPostsViewModel
    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.display_posts_activity)

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                APIClient(APIService.getInstance())
            )
        ).get(DisplayPostsViewModel::class.java)
        binding.recyclerView.adapter = adapter
        viewModel.postsList.observe(this) {
            Log.d(TAG, "onCreate: $it")
            adapter.setPostsListInAdapter(it)
        }
        viewModel.errorMessage.observe(this) {
            Log.d(TAG, "errorMessage: $it")
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        }
        viewModel.getPosts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.settings -> {
                startSettingsActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSettingsActivity() {
        val context = binding.root.context
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }

    companion object {
        private const val TAG = "DisplayPostsActivity"
    }
}