package com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentClickedImageChoosedBinding
import com.example.cookingsocialnetwork.post.PostPage
import com.example.cookingsocialnetwork.post.PostViewModel
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.PostPage2Fragment1
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.PostPage2Fragment1ViewModel

class FragmentClickedImageChosen : Fragment() {

    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapterImageClicked: RecyclerAdapterImageClicked


    lateinit var viewModel: PostPage2Fragment1ViewModel
    lateinit var dataBiding: FragmentClickedImageChoosedBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //set dataBiding
        dataBiding =DataBindingUtil.inflate(inflater, R.layout.fragment_clicked_image_choosed, container, false)

        // set viewModel
        viewModel = ViewModelProvider(requireActivity()).get(PostPage2Fragment1ViewModel::class.java)

        staggeredGridLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        dataBiding.recyclerClickView.layoutManager = staggeredGridLayoutManager

        dataBiding.recyclerClickView.setHasFixedSize(true);

        adapterImageClicked = RecyclerAdapterImageClicked(viewModel.mListUri,
            object : ItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemRemoveClick(uri: Uri, int: Int) {
                    viewModel.removeUriListUris(uri)
                    adapterImageClicked.notifyItemRemoved(int)
                    (parentFragment as PostPage2Fragment1).noticeDataChangeToRecyclerAdapterImageChoosed()
                    if(viewModel.mListUri.isEmpty()){
                       closedFragment()
                    }
                }
            })
        dataBiding.recyclerClickView.adapter = adapterImageClicked

        dataBiding.lifecycleOwner = this

        dataBiding.closeGrid.setOnClickListener{
            closedFragment()
        }
        return dataBiding.root


    }


    private fun closedFragment(){
        val manager = requireActivity().supportFragmentManager
        manager.beginTransaction().remove(this).commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        staggeredGridLayoutManager.removeAllViews()
        dataBiding.recyclerClickView.adapter = null
        closedFragment()

    }

}
