package com.example.cookingsocialnetwork.main.fragment.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentHomeBinding
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts.RandomPostAdapter
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.PostRecentAdapter
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts.TrendingPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val postRecentAdapter by lazy { PostRecentAdapter() }
    private val randomPostAdapter  by lazy { RandomPostAdapter() }
    private val trendingPagingAdapter by lazy { TrendingPagingAdapter() }

    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //recent
        binding.recRecentPosts.adapter = postRecentAdapter
        binding.recRecentPosts.layoutManager = GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch {
            viewModel.flowRecentPosts.collect {
                postRecentAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            postRecentAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBarRecent.isVisible = loadStates.refresh is LoadState.Loading
                binding.progressBarRecentLoadMore.isVisible = loadStates.append is LoadState.Loading
            }
        }


        //random
        binding.recPosts.adapter = randomPostAdapter
        binding.recPosts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        lifecycleScope.launch {
            viewModel.flowRandomPosts.collect {
                randomPostAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            randomPostAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBarRandom.isVisible = loadStates.refresh is LoadState.Loading
                binding.progressBarRandomLoadMore.isVisible = loadStates.append is LoadState.Loading
            }
        }

        //trending
        binding.trendingViewPaper.adapter = trendingPagingAdapter
        setAnimationTrending(binding)
        lifecycleScope.launch {
            viewModel.flowTrendingPosts.collect {
                trendingPagingAdapter.submitData(it)
            }
        }
            return binding.root

        }

    private fun setAnimationTrending(thisBinding: FragmentHomeBinding){
        val handler = Handler()
        var isScrollDown = true;
        thisBinding.trendingViewPaper.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeMessages(0)
                val runnable = Runnable {
                    if(position + 1 == (thisBinding.trendingViewPaper.adapter?.itemCount ?: 0)) isScrollDown = false
                    if(position == 0) isScrollDown = true
                    if(isScrollDown){
                        ++thisBinding.trendingViewPaper.currentItem
                    } else {
                        --thisBinding.trendingViewPaper.currentItem
                    }
                }
                if (position < (thisBinding.trendingViewPaper.adapter?.itemCount ?: 0)) {
                    handler.postDelayed(runnable, 10000)
                }
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                //if (state == SCROLL_STATE_DRAGGING) handler.removeMessages(0)
                if (state != ViewPager2.SCROLL_STATE_IDLE) return
            }
        })
    }

    internal fun reLoad(){
        val ft: FragmentTransaction = this.parentFragmentManager.beginTransaction()
        ft.setReorderingAllowed(false)
        ft.detach(this).attach(this).commit()
    }

}


