package com.example.cookingsocialnetwork.main.fragment.notify.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentFavoriteNotifyBinding

class FavoriteNotifyFragment : Fragment() {
    lateinit var binding: FragmentFavoriteNotifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_notify, container, false)
        return binding.root
    }
}