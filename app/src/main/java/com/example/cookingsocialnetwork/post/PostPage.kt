package com.example.cookingsocialnetwork.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.setting.SettingPage
import kotlinx.android.synthetic.main.activity_post_page.*

class PostPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_page)
        supportActionBar?.hide()
        food_image.setOnClickListener{
            val setting = Intent(this, SettingPage::class.java)
            startActivity(setting)
        }
    }
}