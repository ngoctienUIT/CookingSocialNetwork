package com.example.cookingsocialnetwork.main.fragment.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentProfileBinding
import com.example.cookingsocialnetwork.main.fragment.profile.adapter.ViewPageProfileAdapter
import com.example.cookingsocialnetwork.setting.SettingPage
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfile
import com.example.cookingsocialnetwork.viewfollow.ViewFollowActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


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

        val pageProfile = ViewPageProfileAdapter(this)
        pageProfile.listPost = mutableListOf()
        pageProfile.listFavoritePost = mutableListOf()
        binding.viewPagerProfile.adapter = pageProfile
        binding.viewPagerProfile.isSaveEnabled = false
        TabLayoutMediator(binding.tabLayoutProfile, binding.viewPagerProfile)
        { tab, index ->
            tab.setIcon(
                when (index) {
                    0 -> R.drawable.ic_list_post
                    else -> R.drawable.ic_favorite
                }
            )
            if (index == 0) tab.icon?.setTint(Color.RED)
            else tab.icon?.setTint(Color.parseColor("#C0C0C0"))
        }.attach()

        binding.tabLayoutProfile.addOnTabSelectedListener( object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(Color.RED)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(Color.parseColor("#C0C0C0"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })

        viewModel.getUser.observe(viewLifecycleOwner)
        { user ->
//            Picasso.get().load(user.avatar).into(binding.userAvatar)
            binding.userAvatar.load(user.avatar)
            binding.post.text = user.post.size.toString()
            binding.follower.text = user.followers.size.toString()
            binding.following.text = user.following.size.toString()

            pageProfile.listPost = user.post
            pageProfile.listFavoritePost = user.favourites
            binding.viewPagerProfile.adapter = pageProfile
        }

        return binding.root
    }
}