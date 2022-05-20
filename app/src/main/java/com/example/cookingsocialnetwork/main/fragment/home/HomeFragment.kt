package com.example.cookingsocialnetwork.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentHomeBinding
import com.example.cookingsocialnetwork.main.fragment.home.adapter.PostsAdapter
import com.example.cookingsocialnetwork.main.fragment.home.loadMiniPost.MiniPost
import com.example.cookingsocialnetwork.main.fragment.home.loadMiniPost.MiniPostAdapter
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val postsAdapter by lazy { PostsAdapter() }
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false)


        //Inject here
        DaggerHomeComponent.create().inject(this)

        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.recPosts.adapter = postsAdapter
        binding.recPosts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.listPosts.observe(viewLifecycleOwner) {
            binding.swpRecords.isRefreshing = false;
            postsAdapter.submitList(it)
        }

         //Test cái recyclerView
//        binding.recPosts.adapter = MiniPostAdapter(generate(10))
//        binding.recPosts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.recPosts.setHasFixedSize(true)

        return binding.root


    }
    private fun generate(size: Int): List<MiniPost>{
        val list =  ArrayList<MiniPost>()
        for(i in 0  until size){
            val item = MiniPost(2,"An Bui",2, "hazz", 2,3,4)
            list.add(item)
        }
        return list

    }

}