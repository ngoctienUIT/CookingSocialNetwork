package com.example.cookingsocialnetwork.main.fragment.notify.view.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFollowNotifyBinding

class FollowNotifyFragment : Fragment() {
    lateinit var binding: FragmentFollowNotifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow_notify, container, false)
        return binding.root
    }
}