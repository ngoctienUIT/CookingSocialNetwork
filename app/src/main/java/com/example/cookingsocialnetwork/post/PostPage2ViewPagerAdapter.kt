package com.example.cookingsocialnetwork.post

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.post.PostPage2TabFragment.PostPage2Fragment1
import com.example.cookingsocialnetwork.post.PostPage2TabFragment.PostPage2Fragment2

class PostPage2ViewPagerAdapter(fragment: FragmentManager, lifecycle : Lifecycle): FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> PostPage2Fragment1()
            1 -> PostPage2Fragment2()
            else -> PostPage2Fragment1()
        }
    }

}