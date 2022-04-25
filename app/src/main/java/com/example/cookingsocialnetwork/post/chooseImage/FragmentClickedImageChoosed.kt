package com.example.cookingsocialnetwork.post.chooseImage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentClickedImageChoosedBinding
import com.example.cookingsocialnetwork.post.PostViewModel

class FragmentClickedImageChoosed : Fragment() {

    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapterImageClicked: RecyclerAdapterImageClicked


    lateinit var viewModel: PostViewModel
    lateinit var dataBiding: FragmentClickedImageChoosedBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //set dataBiding
        dataBiding =DataBindingUtil.inflate(inflater, R.layout.fragment_clicked_image_choosed, container, false)
        val factoryViewModel = FragmentClickedImageChoosedViewModelFactory()
        // set viewModel
        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

        staggeredGridLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        dataBiding.recyclerClickView.layoutManager = staggeredGridLayoutManager
        adapterImageClicked = RecyclerAdapterImageClicked(viewModel.mListUriLiveData)
        dataBiding.recyclerClickView.adapter = adapterImageClicked

        dataBiding.lifecycleOwner = this.viewLifecycleOwner
        return dataBiding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        staggeredGridLayoutManager.removeAllViews()
        dataBiding.recyclerClickView.adapter = null

    }

}
