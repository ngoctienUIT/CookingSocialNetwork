package com.example.cookingsocialnetwork.main.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentHomeBinding
import com.example.cookingsocialnetwork.post.loadPost.Post
import com.google.firebase.database.collection.LLRBNode

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false)
        val factory = HomeViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this



        return binding.root
    }
//    private fun generate(size: Int): List<Post>{
//        val list =  ArrayList<Post>()
//        for(i in 0  until size){
//            val item = Post(2,"An Bui",2, "hazz", 2,3,4)
//            list += item
//        }
//        return list
//    }
}