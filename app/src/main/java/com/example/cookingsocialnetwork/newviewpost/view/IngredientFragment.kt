package com.example.cookingsocialnetwork.newviewpost.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentIngredientBinding
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModel
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModelFactory
import com.example.cookingsocialnetwork.newviewpost.adapter.IngredientAdapter

class IngredientFragment : Fragment() {
    lateinit var viewModel: ViewPostViewModel
    lateinit var binding: FragmentIngredientBinding
    var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient, container, false)
        val factory = ViewPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let {
            viewModel.post.observe(it) { post ->
                val ingredientAdapter = IngredientAdapter(post.ingredients)
                val ingredientsLayoutManager = LinearLayoutManager(it)
                binding.ingredient.layoutManager = ingredientsLayoutManager
                binding.ingredient.adapter = ingredientAdapter
            }
        }

        return binding.root
    }
}