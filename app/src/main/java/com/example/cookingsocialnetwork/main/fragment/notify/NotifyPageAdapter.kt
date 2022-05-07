package com.example.cookingsocialnetwork.main.fragment.notify

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.main.fragment.notify.view.AllNotifyFragment
import com.example.cookingsocialnetwork.main.fragment.notify.view.CommentNotifyFragment
import com.example.cookingsocialnetwork.main.fragment.notify.view.FavoriteNotifyFragment
import com.example.cookingsocialnetwork.main.fragment.notify.view.FollowNotifyFragment

class NotifyPageAdapter(fragmentActivity: NotificationsFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4;

    private var all : AllNotifyFragment = AllNotifyFragment()
    private var favorite: FavoriteNotifyFragment = FavoriteNotifyFragment()
    private var comment: CommentNotifyFragment = CommentNotifyFragment()
    private var follow: FollowNotifyFragment = FollowNotifyFragment()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0-> all
            1 -> favorite
            2 -> comment
            else -> follow
        }
    }

}