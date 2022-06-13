package com.example.cookingsocialnetwork.newviewpost.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookingsocialnetwork.newviewpost.ViewPost
import com.example.cookingsocialnetwork.newviewpost.view.CommentFragment
import com.example.cookingsocialnetwork.newviewpost.view.InformationFragment
import com.example.cookingsocialnetwork.newviewpost.view.IngredientFragment
import com.example.cookingsocialnetwork.newviewpost.view.MethodFragment

class PostPageAdapter(activity: ViewPost): FragmentStateAdapter(activity) {
    var id: String = ""

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val frag = InformationFragment()
                frag.id = id
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