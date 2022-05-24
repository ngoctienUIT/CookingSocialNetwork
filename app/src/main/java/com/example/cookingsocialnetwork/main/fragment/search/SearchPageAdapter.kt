package com.example.cookingsocialnetwork.main.fragment.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.main.fragment.search.view.AllSearchFragment
import com.example.cookingsocialnetwork.main.fragment.search.view.PostSearchFragment
import com.example.cookingsocialnetwork.main.fragment.search.view.UserSearchFragment

class SearchPageAdapter(fragmentActivity: SearchFragment): FragmentStateAdapter(fragmentActivity) {
    var query: String = ""

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        when (position)
        {
            0 -> {
                val frag= AllSearchFragment()
                frag.query = query
                return frag
            }
            1 -> {
                val frag= UserSearchFragment()
                frag.query = query
                return frag
            }
            else -> {
                val frag= PostSearchFragment()
                frag.query = query
                return frag
            }
        }
    }
}