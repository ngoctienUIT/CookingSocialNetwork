package com.example.cookingsocialnetwork.post2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.*
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.PostPage2Fragment1

class PostPage2ViewPagerAdapter(fragment: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragment, lifecycle) {
    val fragment1 = PostPage2Fragment1()
    val fragment2 = PostPage2Fragment2()
    val fragment3 = PostPage2Fragment3()
    val fragment4 = PostPage2Fragment4()
    val fragment5 = PostPage2Fragment5()
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->  fragment1
            1 -> fragment2
            2 -> fragment3
            3 -> fragment4
            4 -> fragment5
            else -> fragment1
        }
    }

}