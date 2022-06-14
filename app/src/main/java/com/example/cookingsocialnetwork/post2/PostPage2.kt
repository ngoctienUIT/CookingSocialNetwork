package com.example.cookingsocialnetwork.post2

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.viewfollow.FollowPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class  PostPage2 : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var backBtn: ImageView
    private lateinit var postBtn: Button
//    private lateinit var previewBtn :Button
//    private lateinit var nextBtn: Button

    private val postPageAdapter = PostPage2ViewPagerAdapter(supportFragmentManager, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_post_page_2)

        tabLayout = findViewById(R.id.post_page_tab)
        backBtn = findViewById(R.id.post_page_btn_back)
        viewPager  = findViewById(R.id.post_page_viewPager)
        postBtn = findViewById(R.id.post_page_btn_post)
//        previewBtn = findViewById(R.id.post_page_preview_btn)
//        nextBtn = findViewById(R.id.post_page_next_button)


        viewPager.adapter = postPageAdapter
        viewPager.registerOnPageChangeCallback(
            object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            backBtn.setImageResource(R.drawable.ic_round_close)
                            backBtn.setColorFilter(ContextCompat.getColor(applicationContext, R.color.red))
                        }
                        4 -> {
//                            nextBtn.visibility = View.GONE
//                            previewBtn.text = "Xem lại trước khi đăng"
                        }
                        else -> {
                            backBtn.setImageResource(R.drawable.ic_back_ios)
                            backBtn.setColorFilter(ContextCompat.getColor(applicationContext, R.color.green))
                        }
                    }
                    super.onPageSelected(position)
                }
            }
        )

        TabLayoutMediator(tabLayout, viewPager){
            tabLayoutt, position ->
        }.attach()


        postBtn.setOnClickListener {
            post()
        }

        backBtn.setOnClickListener {
            if(tabLayout.selectedTabPosition == 0){
                finish()
            }else {
                val tab: TabLayout.Tab? = tabLayout.getTabAt(tabLayout.selectedTabPosition - 1)
                tab?.select()
            }
        }

    }
    private fun post(){

        // chỗ này m làm nhá hoàng
        if(postPageAdapter.fragment1.isInitialized()){
            postPageAdapter.fragment1.foodName.text

        }
        if(postPageAdapter.fragment2.isInitialized()){
            postPageAdapter.fragment2.portion.text
            postPageAdapter.fragment2.difficult.rating
            postPageAdapter.fragment2.prepTime.text
            postPageAdapter.fragment2.bakingTime.text
            postPageAdapter.fragment2.restTime.text
        }
        if(postPageAdapter.fragment3.isInitialized()){

        }
        if(postPageAdapter.fragment4.isInitialized()){

        }
        if(postPageAdapter.fragment5.isInitialized()){
            postPageAdapter.fragment5.foodnote.text
        }
        finish()
    }

}


