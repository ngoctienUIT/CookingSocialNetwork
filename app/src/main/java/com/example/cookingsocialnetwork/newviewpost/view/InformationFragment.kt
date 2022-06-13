package com.example.cookingsocialnetwork.newviewpost.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentInformationBinding
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModel
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModelFactory
import com.example.cookingsocialnetwork.profile.ProfileActivity

class InformationFragment : Fragment() {
    lateinit var viewModel: ViewPostViewModel
    lateinit var binding: FragmentInformationBinding
    var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
        val factory = ViewPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let {
            viewModel.user.observe(it) { user ->
                binding.avatar.load(user.avatar)
            }
        }

        binding.avatar.setOnClickListener()
        {
            viewProfile()
        }

        binding.name.setOnClickListener()
        {
            viewProfile()
        }

        binding.userName.setOnClickListener()
        {
            viewProfile()
        }

        return binding.root
    }

    private fun viewProfile()
    {
        val profile = Intent(activity, ProfileActivity::class.java)
        profile.putExtra("user_name", viewModel.user.value?.username)
        startActivity(profile)
    }
}