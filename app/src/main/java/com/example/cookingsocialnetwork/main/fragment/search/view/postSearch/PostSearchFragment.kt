package com.example.cookingsocialnetwork.main.fragment.search.view.postSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentPostSearchBinding
import com.example.cookingsocialnetwork.model.GridAdapterPost
import com.example.cookingsocialnetwork.model.Post

class PostSearchFragment : Fragment() {
    lateinit var query: String
    lateinit var viewModel: PostSearchViewModel
    lateinit var binding: FragmentPostSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_search, container, false)
        val factory = PostSearchFactory()
        viewModel = ViewModelProvider(this, factory).get(PostSearchViewModel::class.java)
        viewModel.query = query
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        var adapter = activity?.let { GridAdapterPost(it, mutableListOf(Post(), Post(), Post(), Post())) }
        binding.gridViewPost.adapter = adapter

        return binding.root
    }
}