package com.example.cookingsocialnetwork.main.fragment.profile

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentProfileBinding
import com.example.cookingsocialnetwork.setting.SettingPage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private var arrayList: ArrayList<PostItem>? = null
    private var gridView : GridView? = null
    private var gridAdapter: GridAdapter? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager2? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container,false)
        val factory = ProfileViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.openSetting.setOnClickListener{
            val settingPage = Intent(activity, SettingPage::class.java)
            startActivity(settingPage)

        }

        viewModel.getUser.observe(viewLifecycleOwner)
        { user->
            Picasso.get().load(user.avatar).into(binding.userAvatar)
            binding.userAvatar.setImageURI(Uri.parse(user.avatar))
            binding.post.text = user.post.size.toString()
            binding.follower.text = user.followers.size.toString()
            binding.following.text = user.following.size.toString()
            binding
        }
        tabLayout = binding.tabLayoutProfile
        viewPager = binding.viewPager2Profile
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager!!.adapter = adapter
        TabLayoutMediator(tabLayout!!, viewPager!!){ tab, position->
            when(position) {
                0 -> {
                    tab.text = "Posted"
                }

                1 -> {
                    tab.text = "Archive"
                }
            }
        }.attach()
        return binding.root

    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabLayout = binding.tabLayoutProfile
        viewPager = binding.viewPager2Profile
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager!!.adapter = adapter
        TabLayoutMediator(tabLayout!!, viewPager!!){ tab, position->
            when(position) {
                0 -> {
                    tab.text = "First"
                }

                1 -> {
                    tab.text = "Second"
                }
            }
        }.attach()
    }*/
}