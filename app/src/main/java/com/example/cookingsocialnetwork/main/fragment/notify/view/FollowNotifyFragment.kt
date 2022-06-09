package com.example.cookingsocialnetwork.main.fragment.notify.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFollowNotifyBinding
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModel
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModelFactory
import com.example.cookingsocialnetwork.main.fragment.notify.adapter.ListAdapterNotifyFollow

class FollowNotifyFragment : Fragment() {
    lateinit var binding: FragmentFollowNotifyBinding
    lateinit var viewModel: NotifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow_notify, container, false)
        val factory = NotifyViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(NotifyViewModel::class.java)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        activity?.let {
            viewModel.follows.observe(it) { list ->
                if (list.size > 0) {
                    val adapter = ListAdapterNotifyFollow(it, list.asReversed())
                    binding.listFollow.isClickable = true
                    binding.listFollow.adapter = adapter
                    binding.listFollow.setOnItemClickListener { _, _, _, _ ->
                    }
                }
            }
        }
        return binding.root
    }
}