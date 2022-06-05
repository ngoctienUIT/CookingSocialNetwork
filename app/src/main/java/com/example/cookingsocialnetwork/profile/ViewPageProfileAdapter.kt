package com.example.cookingsocialnetwork.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.main.fragment.profile.view.FavoritePostFragment
import com.example.cookingsocialnetwork.main.fragment.profile.view.MyPostFragment

class ViewPageProfileAdapter(activity: ProfileActivity): FragmentStateAdapter(activity) {
    var listPost: MutableList<String> = mutableListOf()
    var listFavoritePost: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val frag = MyPostFragment()
                frag.listPost = listPost
                frag
            }

            else -> {
                val frag = FavoritePostFragment()
                frag.listFavorite = listFavoritePost
                frag
            }
        }
    }
}