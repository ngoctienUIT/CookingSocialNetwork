package com.example.cookingsocialnetwork.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentHomeBinding
import com.example.cookingsocialnetwork.main.fragment.home.adapter.PostsAdapter
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
        viewModel.listPosts.observe(viewLifecycleOwner) {
            binding.swpRecords.isRefreshing = false;
            postsAdapter.submitList(it)
        }
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