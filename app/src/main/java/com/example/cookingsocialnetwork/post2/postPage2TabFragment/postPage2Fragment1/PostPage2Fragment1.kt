package com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityAboutPageBinding.inflate
import com.example.cookingsocialnetwork.databinding.PostPage2Fragment1Binding
import com.example.cookingsocialnetwork.main.fragment.home.HomeViewModel
import com.example.cookingsocialnetwork.post2.model.Ingredient
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage.FragmentClickedImageChoosed
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage.RecyclerAdapterImageChoosed

class PostPage2Fragment1: Fragment() {

    private lateinit var viewModel: PostPage2Fragment1ViewModel
    private lateinit var dataBinding: PostPage2Fragment1Binding
    lateinit var foodName :EditText
    //lateinit var foodImage: ImageView  helppppppppppp

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapterImageChoosed: RecyclerAdapterImageChoosed
    private var listImageUri: MutableList<Uri> = mutableListOf()  // image choose from device
    private var listImageUrlFB : MutableList<String> = mutableListOf() // image url from firebase

    var imageList: MutableList<Uri> = ArrayList() ///


    @SuppressLint("NotifyDataSetChanged")
    private var imagesChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            if (result.data?.clipData !=null) {
                //chọn nhiều ảnh
                val count: Int = result.data!!.clipData!!.itemCount
                for (i in 0 until count)
                {
                    val imageUri = result.data!!.clipData!!.getItemAt(i).uri

                    //listImageUri.add(imageUri)
                  //  viewModel.addUriIntoListUris(imageUri)
                    imageList.add(imageUri)
                   // adapterImageChoosed.notifyDataSetChanged()
                }
               // addListUri()

                adapterImageChoosed.notifyDataSetChanged()
            }
            else
            {
                //chọn 1 ảnh
                val imageUri = result.data?.data
                //listImageUri.add(imageUri!!)
                imageList.add(imageUri!!)
                adapterImageChoosed.notifyDataSetChanged()
//                viewModel.addUriIntoListUris(imageUri!!)
//                addListUri()
//                dataBinding.foodImage.setImageURI(imageUri)
            }
        }
    }
    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)

        // pass the constant to compare it
        // with the returned requestCode
        imagesChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addListUri(){

       /* //set RecycleView
        gridLayoutManager = GridLayoutManager(this.context, 3, RecyclerView.VERTICAL, false)
         gridLayoutManager.scrollToPosition(0)
        dataBinding.recyclerViewImage.layoutManager = gridLayoutManager

        adapterImageChoosed = RecyclerAdapterImageChoosed(viewModel.mListUri)
        {

           /* val editGridClickedImageFragment = FragmentClickedImageChoosed()
            val transaction =  requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container_fragmentGridClickedItem, editGridClickedImageFragment)
            transaction.addToBackStack(null)
            transaction.commit()*/
        }
        dataBinding.recyclerViewImage.adapter = adapterImageChoosed
        dataBinding.recyclerViewImage.setHasFixedSize(true)*/


    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun noticeDataChangeToRecyclerAdapterImageChoosed(){
        adapterImageChoosed.notifyDataSetChanged()
    }


    fun isInitialized() = ::foodName.isInitialized

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        dataBinding =  DataBindingUtil.inflate(inflater,R.layout.post_page_2_fragment1, container, false)
        dataBinding.lifecycleOwner = this
        //dataBinding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(PostPage2Fragment1ViewModel::class.java)

        //set RecycleView
        gridLayoutManager = GridLayoutManager(this.context, 3, RecyclerView.VERTICAL, false)
        gridLayoutManager.scrollToPosition(0)
        dataBinding.recyclerViewImage.layoutManager = gridLayoutManager
        //adapterImageChoosed = RecyclerAdapterImageChoosed(viewModel.mListUri){} ///
        adapterImageChoosed = RecyclerAdapterImageChoosed(imageList)
        dataBinding.recyclerViewImage.adapter = adapterImageChoosed

        dataBinding.foodImage.setOnClickListener {
            imageChooser()
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodName = requireView().findViewById(R.id.post_page2_fragment1_food_name)



    }

//    private fun closedFragment(){
//        val manager = requireActivity().supportFragmentManager
//        manager.beginTransaction().remove(this).commit()
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        dataBinding.recyclerViewImage.adapter = null
//        gridLayoutManager.removeAllViews()
//        closedFragment()
//    }
}


