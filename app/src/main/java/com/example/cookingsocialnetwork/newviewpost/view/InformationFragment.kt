package com.example.cookingsocialnetwork.newviewpost.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentInformationBinding
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModel
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModelFactory
import com.example.cookingsocialnetwork.newviewpost.adapter.EventPost
import com.example.cookingsocialnetwork.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth

class InformationFragment : Fragment() {
    lateinit var viewModel: ViewPostViewModel
    lateinit var binding: FragmentInformationBinding
    var id: String = ""
    var eventPost: EventPost? = null

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

            viewModel.post.observe(it)
            { post ->
                if (post.favourites.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString()) > -1)
                    binding.icoFavourite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null))
                else binding.icoFavourite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_border, null))
            }
        }

        binding.btnFavourite.setOnClickListener()
        {
            viewModel.updateFavourite()
        }

        binding.btnShare.setOnClickListener()
        {
            eventPost!!.share()
        }

        binding.btnComment.setOnClickListener()
        {
            eventPost!!.comment()
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