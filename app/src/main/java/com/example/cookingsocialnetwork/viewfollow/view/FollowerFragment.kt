package com.example.cookingsocialnetwork.viewfollow.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFollowerBinding
import com.example.cookingsocialnetwork.main.fragment.search.adapter.ListAdapterUser
import com.example.cookingsocialnetwork.profile.ProfileActivity
import com.example.cookingsocialnetwork.viewfollow.ViewFollowViewModel
import com.example.cookingsocialnetwork.viewfollow.ViewFollowViewModelFactory

class FollowerFragment : Fragment() {
    lateinit var viewModel: ViewFollowViewModel
    lateinit var binding: FragmentFollowerBinding
    var userName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follower, container, false)
        val factory = ViewFollowViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewFollowViewModel::class.java)
        viewModel.userName = userName
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        activity?.let { activity ->
            viewModel.follower.observe(activity)
            { listFollower ->
                if (listFollower.size > 0) {
                    val adapter = ListAdapterUser(activity, listFollower, viewModel.myData.value!!)
                    binding.listFollower.isClickable = true
                    binding.listFollower.adapter = adapter
                    binding.listFollower.setOnItemClickListener { _, _, position, _ ->
                        val profile = Intent(activity, ProfileActivity::class.java)
                        profile.putExtra("user_name", listFollower[position].username)
                        startActivity(profile)
                    }
                }
            }
        }

        return binding.root
    }
}