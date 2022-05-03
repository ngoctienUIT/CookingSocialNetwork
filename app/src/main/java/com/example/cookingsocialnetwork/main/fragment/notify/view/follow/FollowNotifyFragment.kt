package com.example.cookingsocialnetwork.main.fragment.notify.view.follow

import android.os.Bundle
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
import com.example.cookingsocialnetwork.model.adapter.ListAdapterNotifyFollow

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

        activity?.let {
            viewModel.notifys.observe(it) { list ->
                val adapter = ListAdapterNotifyFollow(it, list)
                binding.listFollow.isClickable = true
                binding.listFollow.adapter = adapter
                binding.listFollow.setOnItemClickListener { _, _, _, _ ->

                }
            }
        }
        return binding.root
    }
}