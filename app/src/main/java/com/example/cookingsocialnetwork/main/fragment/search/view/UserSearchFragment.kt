package com.example.cookingsocialnetwork.main.fragment.search.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentUserSearchBinding
import com.example.cookingsocialnetwork.main.fragment.search.SearchViewModel
import com.example.cookingsocialnetwork.main.fragment.search.SearchViewModelFactory
import com.example.cookingsocialnetwork.main.fragment.search.adapter.ListAdapterUser
import com.example.cookingsocialnetwork.profile.ProfileActivity

class UserSearchFragment : Fragment() {
    lateinit var query: String
    lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentUserSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_search, container, false)
        val factory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        viewModel.query = query
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let {
            viewModel._users.observe(it) { list ->
                if (list.size > 0) {
                    val adapter = ListAdapterUser(it, list, viewModel.myData.value!!)
                    binding.listUser.isClickable = true
                    binding.listUser.adapter = adapter
                    binding.listUser.setOnItemClickListener { _, _, position, _ ->
                        val profile = Intent(activity, ProfileActivity::class.java)
                        profile.putExtra("user_name", list[position].username)
                        startActivity(profile)
                    }
                }
            }
        }

        return binding.root
    }
}