package com.example.cookingsocialnetwork.post.chooseImage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentClickedImageChoosedBinding

class FragmentClickedImageChoosed : Fragment() {

    lateinit var viewModel: FragmentClickedImageChoosedViewModel
    lateinit var dataBiding: FragmentClickedImageChoosedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBiding =DataBindingUtil.inflate(inflater, R.layout.fragment_clicked_image_choosed, container, false)
        val factoryViewModel = FragmentClickedImageChoosedViewModelFactory()
        viewModel = ViewModelProvider(this, factoryViewModel).get(FragmentClickedImageChoosedViewModel::class.java)
        dataBiding.viewModelClickedImage = viewModel
            dataBiding.lifecycleOwner = this
        return dataBiding.root

    }
}