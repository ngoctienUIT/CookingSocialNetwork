package com.example.cookingsocialnetwork.main.fragment.notify.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentAllNotifyBinding
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModel
import com.example.cookingsocialnetwork.main.fragment.notify.NotifyViewModelFactory
import com.example.cookingsocialnetwork.model.adapter.viewholder.AdapterViewHolder

class AllNotifyFragment : Fragment() {
    lateinit var binding: FragmentAllNotifyBinding
    lateinit var viewModel: NotifyViewModel
    lateinit var adapter: AdapterViewHolder
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_notify, container, false)
        val factory = NotifyViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(NotifyViewModel::class.java)

        layoutManager = LinearLayoutManager(activity)
        adapter = AdapterViewHolder()

        adapter.onLoadMore
        run {
            loadMore()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            reload()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun reload() {
        binding.recyclerView.post {
            adapter.reload(mutableListOf())
        }
    }

    private fun loadMore() {
        binding.recyclerView.post {
            adapter.loadMore(mutableListOf())
        }
    }
}