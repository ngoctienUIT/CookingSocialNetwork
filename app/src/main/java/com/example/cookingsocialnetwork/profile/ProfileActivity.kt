package com.example.cookingsocialnetwork.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.databinding.ActivityProfileBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra("user_name")!!
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val factory = ProfileViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        viewModel.userName = userName
        viewModel.listenToData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        supportActionBar?.hide()

        binding.backProfile.setOnClickListener()
        {
            finish()
        }

        binding.follow.setOnClickListener()
        {
//            val checkFollow = viewModel.getUser.value.following.indexOf(userArrayList[position].username) != -1
//            if (checkFollow) binding.follow.text = "Unfollow"
        }

        viewModel.getUser.observe(this)
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
    }
}