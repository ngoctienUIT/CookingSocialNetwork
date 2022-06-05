package com.example.cookingsocialnetwork.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentProfileBinding
import com.example.cookingsocialnetwork.main.fragment.profile.adapter.ViewPageProfileAdapter
import com.example.cookingsocialnetwork.main.fragment.search.SearchPageAdapter
import com.example.cookingsocialnetwork.setting.SettingPage
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val factory = ProfileViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.openSetting.setOnClickListener {
            val settingPage = Intent(activity, SettingPage::class.java)
            startActivity(settingPage)
        }

        viewModel.getUser.observe(viewLifecycleOwner)
        { user ->
//            binding.userAvatar.setImageURI(Uri.parse(user.avatar))
            Picasso.get().load(user.avatar).into(binding.userAvatar)
            binding.post.text = user.post.size.toString()
            binding.follower.text = user.followers.size.toString()
            binding.following.text = user.following.size.toString()

            val pageProfile = ViewPageProfileAdapter(this)
            pageProfile.listPost = user.post
            pageProfile.listFavoritePost = user.favourites

            binding.viewPagerProfile.adapter = pageProfile
            binding.viewPagerProfile.isSaveEnabled = false
            TabLayoutMediator(binding.tabLayoutProfile, binding.viewPagerProfile)
            { tab, index ->
                tab.text = when (index) {
                    0 -> {
                        "Bài viết"
                    }
                    else -> {
                        "Yêu thích"
                    }
                }
            }.attach()
        }

        return binding.root
    }
}