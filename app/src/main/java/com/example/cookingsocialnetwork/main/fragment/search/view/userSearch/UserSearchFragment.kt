package com.example.cookingsocialnetwork.main.fragment.search.view.userSearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentUserSearchBinding
import com.example.cookingsocialnetwork.model.ListAdapterUser

class UserSearchFragment : Fragment() {
    lateinit var query: String
    lateinit var viewModel: UserSearchViewModel
    lateinit var binding: FragmentUserSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_search, container, false)
        val factory = UserSearchFactory()
        viewModel = ViewModelProvider(this, factory).get(UserSearchViewModel::class.java)
        viewModel.query = query
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let {
            viewModel._users.observe(it) { list ->
                val adapter = ListAdapterUser(it, list, viewModel.myData.value!!)
                binding.listUser.isClickable = true
                binding.listUser.adapter = adapter
                binding.listUser.setOnItemClickListener{
                    parent, view, positon, id ->
                        Log.w("Username", list[positon].username)
                }
            }
        }

        return binding.root
    }
}