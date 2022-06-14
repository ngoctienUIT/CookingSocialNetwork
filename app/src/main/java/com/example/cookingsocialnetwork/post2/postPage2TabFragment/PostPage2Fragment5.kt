package com.example.cookingsocialnetwork.post2.postPage2TabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.cookingsocialnetwork.R
import com.google.android.material.textview.MaterialTextView


class PostPage2Fragment5 : Fragment() {

    lateinit var foodNote: EditText
    fun isInitialized() = ::foodNote.isInitialized


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.post_page_2_fragment5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodNote = requireView().findViewById(R.id.post_page2_fragment5_food_note)


    }


}