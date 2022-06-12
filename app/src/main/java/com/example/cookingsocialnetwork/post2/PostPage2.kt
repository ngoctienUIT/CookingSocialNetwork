package com.example.cookingsocialnetwork.post2

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
    private lateinit var closeBtn: Button
    private lateinit var previewBtn :Button
    private lateinit var nextBtn: Button
    private val postPageAdapter = PostPage2ViewPagerAdapter(supportFragmentManager, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_post_page_2)

        tabLayout = findViewById(R.id.post_page_tab)
        backBtn = findViewById(R.id.post_page_btn_back)
        viewPager  = findViewById(R.id.post_page_viewPager)
        closeBtn = findViewById(R.id.post_page_btn_close)
        previewBtn = findViewById(R.id.post_page_preview_btn)
        nextBtn = findViewById(R.id.post_page_next_button)


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
                            previewBtn.visibility = View.GONE
                            nextBtn.text = "Xem lại trước khi đăng"
                        }
                        else -> {
                            backBtn.setImageResource(R.drawable.ic_back_ios)
                            backBtn.setColorFilter(ContextCompat.getColor(applicationContext, R.color.green))
                            nextBtn.text = "Tiếp theo"
                            previewBtn.visibility = View.VISIBLE
                        }
                    }
                    super.onPageSelected(position)
                }
            }
        )
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager){
            tabLayoutt, position ->
        }.attach()


        closeBtn.setOnClickListener {
            finish()
        }

        nextBtn.setOnClickListener {
            val tab: TabLayout.Tab? = tabLayout.getTabAt(tabLayout.selectedTabPosition + 1)
            tab?.select()
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
}


