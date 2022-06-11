package com.example.cookingsocialnetwork.main.fragment.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentHomeBinding
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts.RandomPostAdapter
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts.TrendingAdapter
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts.TrendingSlide
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.PostRecentAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment(), PostRecentAdapter.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val postRecentAdapter by lazy { PostRecentAdapter() }
    private val randomPostAdapter  by lazy { RandomPostAdapter() }
    private val  trendingSliderAdapter by lazy {TrendingAdapter()}

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        //Inject here
        DaggerHomeComponent.create().inject(this)

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

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
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                binding.progressBarLoadMore.isVisible = loadStates.append is LoadState.Loading
            }
        }

        //recent
        binding.recRecentPosts.adapter = postRecentAdapter
        binding.recRecentPosts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.listPosts.observe(viewLifecycleOwner) {
            binding.swpRecords.isRefreshing = false;
            postRecentAdapter.submitList(it)
        }
        //trending

        //binding.trendingViewPaper.adapter = trendingSliderAdapter
        lifecycleScope.launch {
            val x = viewModel.flowTrendingPosts.collect {
                trendingSliderAdapter.submitData(it)
            }
            x.run {
                binding.trendingViewPaper.adapter = trendingSliderAdapter
            }
        }


            // binding.trendingViewPaper.adapter = trendingSliderAdapter
            /*binding.trending.adapter = trendingSliderAdapter
        setAnimationTrending(binding)*/

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

   /* private val trendingSliderAdapter
        get() = TrendingAdapter(
            listOf(
                TrendingSlide(
                    R.drawable.food_picker,
                    "1"
                ),
                TrendingSlide(
                    R.drawable.food_picker,
                    "2"
                ),
                TrendingSlide(
                    R.drawable.food_picker,
                    "3"
                )
            )
        )*/
    override fun onCommentClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }
}

