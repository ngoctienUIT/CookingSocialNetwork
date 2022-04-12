package com.example.cookingsocialnetwork.main.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {
    lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container,false)
        val factory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(SearchViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.viewSearchPage.visibility = View.GONE
        binding.tabSearch.visibility = View.GONE

        binding.viewSearchPage.adapter = SearchPageAdapter(this)
        binding.viewSearchPage.isSaveEnabled = false
        TabLayoutMediator(binding.tabSearch, binding.viewSearchPage)
        {
            tab, index ->
            tab.text = when (index)
            {
                0-> {getString(R.string.user)}
                1-> {getString(R.string.post_title)}
                else -> {getString(R.string.user)}
            }
        }.attach()

        binding.search.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    binding.viewSearchPage.visibility = View.VISIBLE
                    binding.tabSearch.visibility = View.VISIBLE
                    return false
                }
            }
        )

        return binding.root
    }
}