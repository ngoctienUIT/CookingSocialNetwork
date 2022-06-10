package com.example.cookingsocialnetwork.post2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class  PostPage2 : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_post_page_2)

        tabLayout = findViewById(R.id.post_page_tab)
        viewPager  = findViewById(R.id.post_page_viewPager)

        viewPager.adapter = PostPage2ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager){
            tabLayout, position ->
        }.attach()
    }
}