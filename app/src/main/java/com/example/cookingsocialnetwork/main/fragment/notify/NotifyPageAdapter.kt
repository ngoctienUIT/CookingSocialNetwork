package com.example.cookingsocialnetwork.main.fragment.notify

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.main.fragment.notify.view.comment.CommentNotifyFragment
import com.example.cookingsocialnetwork.main.fragment.notify.view.favorite.FavoriteNotifyFragment
import com.example.cookingsocialnetwork.main.fragment.notify.view.follow.FollowNotifyFragment

class NotifyPageAdapter(fragmentActivity: NotificationsFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3;

    private var favorite: FavoriteNotifyFragment = FavoriteNotifyFragment()
    private var comment: CommentNotifyFragment = CommentNotifyFragment()
    private var follow: FollowNotifyFragment = FollowNotifyFragment()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> favorite
            1 -> comment
            else -> follow
        }
    }

}