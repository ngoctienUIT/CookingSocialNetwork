package com.example.cookingsocialnetwork.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityPostPageBinding
import com.example.cookingsocialnetwork.databinding.ActivityViewPostPageBinding

class ViewPostPage: AppCompatActivity() {
    private lateinit var dataBinding: ActivityViewPostPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        dataBinding= DataBindingUtil.setContentView(this, R.layout.activity_view_post_page)
        setContentView(dataBinding.root)
        dataBinding.backViewPost.setOnClickListener()
        {
            finish()
        }
    }
}