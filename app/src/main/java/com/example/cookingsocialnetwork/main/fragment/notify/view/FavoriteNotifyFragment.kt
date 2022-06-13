package com.example.cookingsocialnetwork.main.fragment.notify.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFavoriteNotifyBinding
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModel
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModelFactory
import com.example.cookingsocialnetwork.main.fragment.notify.adapter.ListAdapterNotifyFavorite
import com.example.cookingsocialnetwork.newviewpost.ViewPost
import com.example.cookingsocialnetwork.viewpost.ViewFullPost

class FavoriteNotifyFragment : Fragment() {
    lateinit var binding: FragmentFavoriteNotifyBinding
    lateinit var viewModel: NotifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_notify, container, false)
        val factory = NotifyViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(NotifyViewModel::class.java)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        activity?.let {
            viewModel.favorites.observe(it) { list ->
                if (list.size > 0) {
                    val adapter = ListAdapterNotifyFavorite(it, list.asReversed())
                    binding.listFavorite.isClickable = true
                    binding.listFavorite.adapter = adapter
                    binding.listFavorite.setOnItemClickListener { _, _, position, _ ->
                        val fullPost = Intent(it, ViewPost::class.java)
                        fullPost.putExtra("id_post", list[position].id)
                        it.startActivity(fullPost)
                    }
                }
            }
        }

        return binding.root
    }
}