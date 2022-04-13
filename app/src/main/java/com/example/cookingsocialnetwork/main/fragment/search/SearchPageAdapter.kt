package com.example.cookingsocialnetwork.main.fragment.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.main.fragment.search.view.postSearch.PostSearchFragment
import com.example.cookingsocialnetwork.main.fragment.search.view.userSearch.UserSearchFragment

class SearchPageAdapter(fragmentActivity: SearchFragment): FragmentStateAdapter(fragmentActivity) {
    var query: String = ""

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        when (position)
        {
            0 -> {
                var frag= UserSearchFragment()
                frag.query = query
                return frag
            }
            1 -> {
                var frag= PostSearchFragment()
                frag.query = query
                return frag
            }
            else -> {
                var frag= UserSearchFragment()
                frag.query = query
                return frag
            }
        }
    }
}