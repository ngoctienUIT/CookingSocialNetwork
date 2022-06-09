package com.example.cookingsocialnetwork.main.fragment.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentAllSearchBinding
import com.example.cookingsocialnetwork.main.fragment.search.SearchViewModel
import com.example.cookingsocialnetwork.main.fragment.search.SearchViewModelFactory
import com.example.cookingsocialnetwork.main.fragment.search.adapterviewholder.AllSearchAdapter

class AllSearchFragment : Fragment() {
    lateinit var query: String
    lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentAllSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_search, container, false)
        val factory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        viewModel.query = query
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        activity?.let {
            viewModel._posts.observe(it)
            { listPost ->
                viewModel._users.observe(it)
                { listUser ->
                    val index: Int = if (listUser.size < 6) listUser.size
                    else 5
                    val layoutManager = GridLayoutManager(activity, 2)
                    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position < index) 2
                            else 1
                        }
                    }
                    binding.recyclerView.layoutManager = layoutManager
                    val adapter = AllSearchAdapter(listUser, listPost)
                    binding.recyclerView.adapter = adapter
                }
            }
        }

        return binding.root
    }
}