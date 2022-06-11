package com.example.cookingsocialnetwork.post2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.viewfollow.FollowPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class  PostPage2 : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var closeBtn: Button
    private val postPageAdapter = PostPage2ViewPagerAdapter(supportFragmentManager, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_post_page_2)

        tabLayout = findViewById(R.id.post_page_tab)
        viewPager  = findViewById(R.id.post_page_viewPager)

        viewPager.adapter = postPageAdapter

        TabLayoutMediator(tabLayout, viewPager){
            tabLayout, position ->
        }.attach()

        closeBtn = findViewById(R.id.post_page_btn_close)
        closeBtn.setOnClickListener {
            println(postPageAdapter.fragment2.portion.text)
        }
    }
}