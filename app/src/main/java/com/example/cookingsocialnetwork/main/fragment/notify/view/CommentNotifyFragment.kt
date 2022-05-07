package com.example.cookingsocialnetwork.main.fragment.notify.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentCommentNotifyBinding
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModel
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModelFactory
import com.example.cookingsocialnetwork.model.adapter.ListAdapterNotifyComment

class CommentNotifyFragment : Fragment() {
    lateinit var binding: FragmentCommentNotifyBinding
    lateinit var viewModel: NotifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_notify, container, false)
        val factory = NotifyViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(NotifyViewModel::class.java)

        activity?.let {
            viewModel.comments.observe(it) { list ->
                val adapter = ListAdapterNotifyComment(it, list)
                binding.listComment.isClickable = true
                binding.listComment.adapter = adapter
                binding.listComment.setOnItemClickListener { _, _, _, _ ->

                }
            }
        }
        return binding.root
    }
}