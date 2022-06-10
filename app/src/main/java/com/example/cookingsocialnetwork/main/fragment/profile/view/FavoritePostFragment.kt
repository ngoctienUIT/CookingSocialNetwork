package com.example.cookingsocialnetwork.main.fragment.profile.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFavoritePostBinding
import com.example.cookingsocialnetwork.main.fragment.profile.adapter.PostAdapter

class FavoritePostFragment : Fragment() {
    var listFavorite:MutableList<String> = mutableListOf()
    private lateinit var viewModel: PostViewModel
    private lateinit var binding: FragmentFavoritePostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_post, container, false)
        val factory = PostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        viewModel.listPost = listFavorite
        viewModel.listenToData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val layoutManager = GridLayoutManager(activity, 3)
        binding.favoritePost.layoutManager = layoutManager

        activity?.let {
            viewModel.getPost.observe(it) { postList ->
                if (postList.size > 0) {
                    binding.favoritePost.adapter = PostAdapter(postList.asReversed())
                }
            }
        }

        return binding.root
    }
}