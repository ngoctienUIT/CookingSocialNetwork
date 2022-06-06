package com.example.cookingsocialnetwork.viewfollow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.databinding.ActivityViewFollowBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewFollowActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewFollowBinding
    private lateinit var viewModel: ViewFollowViewModel
    private var userName: String = ""
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra("user_name")!!
        index = intent.getIntExtra("index", 0)
        binding = ActivityViewFollowBinding.inflate(layoutInflater)
        val factory = ViewFollowViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewFollowViewModel::class.java)
        viewModel.userName = userName
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val followAdapter = FollowPageAdapter(this)
        followAdapter.userName = userName

        binding.backViewFollow.setOnClickListener()
        {
            finish()
        }

        binding.viewPage.adapter = followAdapter
        binding.viewPage.isSaveEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPage)
        { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Following"
                }
                else -> {
                    "Follower"
                }
            }
        }.attach()

        val tab = binding.tabLayout.getTabAt(index)
        tab?.select()

        setContentView(binding.root)
        supportActionBar?.hide()
    }
}