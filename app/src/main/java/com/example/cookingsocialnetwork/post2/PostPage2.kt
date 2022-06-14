package com.example.cookingsocialnetwork.post2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityPostPage2Binding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class  PostPage2 : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var backBtn: ImageView
    private lateinit var postBtn: Button
    private lateinit var viewModel: PostPage2ViewModel
    private lateinit var binding: ActivityPostPage2Binding

//    private lateinit var previewBtn :Button
//    private lateinit var nextBtn: Button

    private lateinit var postPageAdapter : PostPage2ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_post_page_2)

        viewModel = ViewModelProvider(this).get(PostPage2ViewModel::class.java)
        binding =ActivityPostPage2Binding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        tabLayout = findViewById(R.id.post_page_tab)
        backBtn = findViewById(R.id.post_page_btn_back)
        viewPager  = findViewById(R.id.post_page_viewPager)
        postBtn = findViewById(R.id.post_page_btn_post)
//        previewBtn = findViewById(R.id.post_page_preview_btn)
//        nextBtn = findViewById(R.id.post_page_next_button)


        postPageAdapter = PostPage2ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = postPageAdapter
        binding.postPageViewPager.registerOnPageChangeCallback(
            object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            binding.postPageBtnBack.setImageResource(R.drawable.ic_round_close)
                            binding.postPageBtnBack.setColorFilter(ContextCompat.getColor(applicationContext, R.color.red))
                        }
                        4 -> {
//                            nextBtn.visibility = View.GONE
//                            previewBtn.text = "Xem lại trước khi đăng"
                        }
                        else -> {
                            binding.postPageBtnBack.setImageResource(R.drawable.ic_back_ios)
                            binding.postPageBtnBack.setColorFilter(ContextCompat.getColor(applicationContext, R.color.green))
                        }
                    }
                    super.onPageSelected(position)

                    TabLayoutMediator(binding.postPageTab, binding.postPageViewPager){
                            tabLayoutt, position ->
                    }.attach()
                }

            }

        )


        binding.postPageBtnPost.setOnClickListener {
            post()
        }

        binding.postPageBtnBack.setOnClickListener {
            if(binding.postPageTab.selectedTabPosition == 0){
                finish()
            }else {
                val tab: TabLayout.Tab? = binding.postPageTab.getTabAt(binding.postPageTab.selectedTabPosition - 1)
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


