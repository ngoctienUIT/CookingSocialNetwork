package com.example.cookingsocialnetwork.viewfollow.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFollowingBinding
import com.example.cookingsocialnetwork.main.fragment.search.adapter.ListAdapterUser
import com.example.cookingsocialnetwork.profile.ProfileActivity
import com.example.cookingsocialnetwork.viewfollow.ViewFollowViewModel
import com.example.cookingsocialnetwork.viewfollow.ViewFollowViewModelFactory

class FollowingFragment : Fragment() {
    var userName: String = ""
    lateinit var viewModel: ViewFollowViewModel
    lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        val factory = ViewFollowViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewFollowViewModel::class.java)
        viewModel.userName = userName
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let { activity ->
            viewModel.following.observe(activity)
            { listFollowing ->
                if (listFollowing.size > 0) {
                    val adapter = ListAdapterUser(activity, listFollowing, viewModel.myData.value!!)
                    binding.listFollowing.isClickable = true
                    binding.listFollowing.adapter = adapter
                    binding.listFollowing.setOnItemClickListener { _, _, position, _ ->
                        val profile = Intent(activity, ProfileActivity::class.java)
                        profile.putExtra("user_name", listFollowing[position].username)
                        startActivity(profile)
                    }
                }
            }
        }

        return binding.root
    }
}