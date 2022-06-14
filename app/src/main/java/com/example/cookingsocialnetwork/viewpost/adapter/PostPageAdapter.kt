package com.example.cookingsocialnetwork.viewpost.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.viewpost.ViewPost
import com.example.cookingsocialnetwork.viewpost.view.CommentFragment
import com.example.cookingsocialnetwork.viewpost.view.InformationFragment
import com.example.cookingsocialnetwork.viewpost.view.IngredientFragment
import com.example.cookingsocialnetwork.viewpost.view.MethodFragment

class PostPageAdapter(activity: ViewPost): FragmentStateAdapter(activity) {
    var id: String = ""
    var eventPost: EventPost? = null

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val frag = InformationFragment()
                frag.id = id
                frag.eventPost = eventPost
                frag
            }
            1 -> {
                val frag = IngredientFragment()
                frag.id = id
                frag
            }
            2 -> {
                val frag = MethodFragment()
                frag.id = id
                frag
            }
            else -> {
                val frag = CommentFragment()
                frag.id = id
                frag
            }
        }
    }
}