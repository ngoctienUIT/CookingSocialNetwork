package com.example.cookingsocialnetwork.post2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.post2.PostPage2TabFragment.*

class PostPage2ViewPagerAdapter(fragment: FragmentManager, lifecycle : Lifecycle): FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return 5;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> PostPage2Fragment1()
            1 -> PostPage2Fragment2()
            2 -> PostPage2Fragment3()
            3 -> PostPage2Fragment4()
            4 -> PostPage2Fragment5()
            else -> PostPage2Fragment1()
        }
    }

}