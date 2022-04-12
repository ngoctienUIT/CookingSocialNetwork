package com.example.cookingsocialnetwork.main.fragment.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.main.fragment.search.view.PostSearchFragment
import com.example.cookingsocialnetwork.main.fragment.search.view.UserSearchFragment

class SearchPageAdapter(fragmentActivity: SearchFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position)
        {
            0 -> {UserSearchFragment()}
            1 -> {PostSearchFragment()}
            else -> {UserSearchFragment()}
        }
    }
}