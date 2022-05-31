package com.example.cookingsocialnetwork.viewpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityViewFullPostBinding
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfileViewModel
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfileViewModelFactory
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator

class ViewFullPost : AppCompatActivity() {
    lateinit var binding: ActivityViewFullPostBinding
    private lateinit var viewModel: ViewFullPostViewModel
    private lateinit var id: String
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("id_post")!!
        binding = ActivityViewFullPostBinding.inflate(layoutInflater)
        val factory = ViewFullPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewFullPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        supportActionBar?.hide()

        binding.backViewFullPost.setOnClickListener()
        {
            finish()
        }

        viewModel.user.observe(this)
        {
            Picasso.get().load(it.avatar).into(binding.avatar)
        }

        //slider images
        viewModel.post.observe(this){
            it.images.let { list ->
            viewPagerAdapter = ImageSlideAdapter(this, list )
            binding.viewpager.adapter = viewPagerAdapter
            indicator = binding.indicator
            indicator.setViewPager(binding.viewpager)

        }
        }
    }
}