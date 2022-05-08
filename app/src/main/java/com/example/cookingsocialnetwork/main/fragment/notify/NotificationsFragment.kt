package com.example.cookingsocialnetwork.main.fragment.notify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentNotificationsBinding
import com.google.android.material.tabs.TabLayoutMediator


class NotificationsFragment : Fragment() {
    lateinit var binding: FragmentNotificationsBinding
    lateinit var viewModel: NotifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        val factory = NotifyViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(NotifyViewModel::class.java)

        val notifyPageAdapter = NotifyPageAdapter(this)
        binding.viewNotifyPage.adapter = notifyPageAdapter
        binding.viewNotifyPage.isSaveEnabled = false

        TabLayoutMediator(binding.tabNotify, binding.viewNotifyPage)
        { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "All"
                }
                1 -> {
                    getString(R.string.favorite)
                }
                2 -> {
                    getString(R.string.comment)
                }
                else -> {
                    getString(R.string.follow)
                }
            }
        }.attach()

        return binding.root
    }
}