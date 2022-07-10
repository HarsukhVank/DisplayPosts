package com.hv.displayposts.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hv.displayposts.R
import com.hv.displayposts.databinding.SettingsActivityBinding
import com.hv.displayposts.utility.Constants

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.settings_activity)
        binding.addButton.setOnClickListener {
            if (binding.email.text.trim().isEmpty()) {
                Toast.makeText(this, "Please add email address", Toast.LENGTH_LONG).show()
            } else {
                val sharedPreferences: SharedPreferences = this.getSharedPreferences(
                    PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(Constants.PREF_EMAIL, binding.email.text.toString())
                editor.apply()
                Toast.makeText(this, "Email address added", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val PREFERENCE_NAME = "DisplayPostsPref"
    }
}