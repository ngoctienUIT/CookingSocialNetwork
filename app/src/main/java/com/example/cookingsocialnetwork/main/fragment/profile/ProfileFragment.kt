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
import com.example.cookingsocialnetwork.profile.ProfileActivity
import com.example.cookingsocialnetwork.setting.SettingPage
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfile
import com.example.cookingsocialnetwork.viewfollow.ViewFollowActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
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

        binding.editProfile.setOnClickListener()
        {
            val settingPage = Intent(activity, SettingChangeProfile::class.java)
            startActivity(settingPage)
        }

        binding.btnFollower.setOnClickListener()
        {
            val viewFollower = Intent(activity, ViewFollowActivity::class.java)
            viewFollower.putExtra("user_name", FirebaseAuth.getInstance().currentUser?.email.toString())
            viewFollower.putExtra("index", 1)
            startActivity(viewFollower)
        }

        binding.btnFollowing.setOnClickListener()
        {
            val viewFollowing = Intent(activity, ViewFollowActivity::class.java)
            viewFollowing.putExtra("user_name", FirebaseAuth.getInstance().currentUser?.email.toString())
            viewFollowing.putExtra("index", 0)
            startActivity(viewFollowing)
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