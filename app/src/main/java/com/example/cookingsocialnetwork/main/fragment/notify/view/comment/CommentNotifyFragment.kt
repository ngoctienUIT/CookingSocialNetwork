package com.example.cookingsocialnetwork.main.fragment.notify.view.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentCommentNotifyBinding

class CommentNotifyFragment : Fragment() {
    lateinit var binding: FragmentCommentNotifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_notify, container, false)
        return binding.root
    }
}