package com.example.cookingsocialnetwork.viewpost.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentMethodBinding
import com.example.cookingsocialnetwork.viewpost.ViewPostViewModel
import com.example.cookingsocialnetwork.viewpost.ViewPostViewModelFactory
import com.example.cookingsocialnetwork.viewpost.adapter.MethodsAdapter

class MethodFragment : Fragment() {
    lateinit var viewModel: ViewPostViewModel
    lateinit var binding: FragmentMethodBinding
    var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_method, container, false)
        val factory = ViewPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let {
            viewModel.post.observe(it) { post ->
                val methodsAdapter = MethodsAdapter(post.methods)
                val methodsLayoutManager = LinearLayoutManager(it)
                binding.method.layoutManager = methodsLayoutManager
                binding.method.adapter = methodsAdapter
            }
        }

        return binding.root
    }
}