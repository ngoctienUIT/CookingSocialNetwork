package com.example.cookingsocialnetwork.main.fragment.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentPostSearchBinding
import com.example.cookingsocialnetwork.main.fragment.search.SearchViewModel
import com.example.cookingsocialnetwork.main.fragment.search.SearchViewModelFactory
import com.example.cookingsocialnetwork.model.adapter.GridAdapterPost
import com.example.cookingsocialnetwork.model.data.Post

class PostSearchFragment : Fragment() {
    lateinit var query: String
    lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentPostSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_search, container, false)
        val factory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        viewModel.query = query
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val adapter = activity?.let { GridAdapterPost(it, mutableListOf(Post(), Post(), Post(), Post())) }
        binding.gridViewPost.adapter = adapter

        return binding.root
    }
}