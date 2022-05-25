package com.example.cookingsocialnetwork.main.fragment.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentHomeBinding
import com.example.cookingsocialnetwork.intro.IntroSlide
import com.example.cookingsocialnetwork.intro.IntroSliderAdapter
import com.example.cookingsocialnetwork.main.fragment.home.adapter.PostsAdapter
import com.example.cookingsocialnetwork.main.fragment.home.loadMiniPost.MiniPost
import com.example.cookingsocialnetwork.main.fragment.home.loadMiniPost.TrendingAdapter
import javax.inject.Inject

class HomeFragment : Fragment(), PostsAdapter.OnClickListener {

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
        binding.trending.adapter = introSliderAdapter
        val handler = Handler()
        var isScrollDown = true;
        binding.trending.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeMessages(0)
                val runnable = Runnable {
                    if( position+1 == binding.trending.adapter?.itemCount?: 0 ) isScrollDown = false
                    if(position == 0) isScrollDown = true
                    if(isScrollDown){
                        ++binding.trending.currentItem
                    } else {
                        --binding.trending.currentItem
                    }


                }
                if (position < binding.trending.adapter?.itemCount ?: 0) {
                    handler.postDelayed(runnable, 1000)
                }
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                //if (state == SCROLL_STATE_DRAGGING) handler.removeMessages(0)
                if(state == ViewPager2.SCROLL_STATE_IDLE){

                }
            }
        })
        binding.recPosts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.listPosts.observe(viewLifecycleOwner) {
            binding.swpRecords.isRefreshing = false;
            postsAdapter.submitList(it)
        }



        return binding.root


    }


    override fun onCommentClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }
    private val introSliderAdapter
        get() = IntroSliderAdapter(
            listOf(
                IntroSlide(
                    "Ex 1",
                    "This is example 1",
                    R.drawable.example_image1
                ),
                IntroSlide(
                    "Ex 2",
                    "This is example 2",
                    R.drawable.example_image2
                ),
                IntroSlide(
                    "Ex 3",
                    "This is example 3",
                    R.drawable.example_image3
                )
            )
        )


}