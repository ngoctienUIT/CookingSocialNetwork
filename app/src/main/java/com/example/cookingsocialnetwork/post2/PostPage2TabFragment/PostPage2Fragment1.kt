package com.example.cookingsocialnetwork.post2.PostPage2TabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.cookingsocialnetwork.R

class PostPage2Fragment1: Fragment() {
    lateinit var foodName :EditText
    //lateinit var foodImage: ImageView          helppppppppppp
    fun isInitialized() = ::foodName.isInitialized
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        return inflater.inflate(R.layout.post_page_2_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodName = requireView().findViewById(R.id.post_page2_fragment1_food_name)
    }
}


