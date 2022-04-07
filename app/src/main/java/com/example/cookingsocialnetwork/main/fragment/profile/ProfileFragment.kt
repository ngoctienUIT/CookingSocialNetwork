package com.example.cookingsocialnetwork.main.fragment.profile

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
import com.example.cookingsocialnetwork.databinding.FragmentProfileBinding
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfile

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container,false)
        val factory = ProfileViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }


    fun startSettingChangeProfile()
    {
        val intent = Intent (activity, SettingChangeProfile::class.java)
        activity?.startActivity(intent)
    }

}