package com.example.cookingsocialnetwork.main.fragment.notify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentNotificationsBinding
import com.google.android.material.tabs.TabLayoutMediator

class NotificationsFragment : Fragment() {
    lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)

        var notifyPageAdapter: NotifyPageAdapter = NotifyPageAdapter(this)
        binding.viewNotifyPage.adapter = notifyPageAdapter

        TabLayoutMediator(binding.tabNotify, binding.viewNotifyPage)
        { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.favorite)
                }
                1 -> {
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