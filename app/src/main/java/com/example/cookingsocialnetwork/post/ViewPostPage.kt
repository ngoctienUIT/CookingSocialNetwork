package com.example.cookingsocialnetwork.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingsocialnetwork.R

class ViewPostPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_view_post_page)
    }
}