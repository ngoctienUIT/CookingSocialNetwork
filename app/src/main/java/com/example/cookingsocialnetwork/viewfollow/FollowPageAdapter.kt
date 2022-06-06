package com.example.cookingsocialnetwork.viewfollow

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.viewfollow.ViewFollowActivity
import com.example.cookingsocialnetwork.viewfollow.view.FollowerFragment
import com.example.cookingsocialnetwork.viewfollow.view.FollowingFragment

class FollowPageAdapter(activity: ViewFollowActivity): FragmentStateAdapter(activity) {
    var userName: String = ""

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val frag = FollowingFragment()
                frag.userName = userName
                frag
            }
            else -> {
                val frag = FollowerFragment()
                frag.userName = userName
                frag
            }
        }
    }
}