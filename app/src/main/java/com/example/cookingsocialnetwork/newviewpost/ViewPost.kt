package com.example.cookingsocialnetwork.newviewpost

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityViewPostBinding
import com.example.cookingsocialnetwork.databinding.LayoutDeleteBinding
import com.example.cookingsocialnetwork.model.data.User
import com.example.cookingsocialnetwork.newviewpost.adapter.PostPageAdapter
import com.example.cookingsocialnetwork.viewpost.adapter.ImageSlideAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.relex.circleindicator.CircleIndicator

class ViewPost : AppCompatActivity() {
    lateinit var binding: ActivityViewPostBinding
    private lateinit var viewModel: ViewPostViewModel
    private lateinit var id: String
    private lateinit var viewPagerAdapter: ImageSlideAdapter
    private lateinit var indicator: CircleIndicator

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("id_post")!!
        binding = ActivityViewPostBinding.inflate(layoutInflater)
        val factory = ViewPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        supportActionBar?.hide()

        val postAdapter = PostPageAdapter(this)
        postAdapter.id = id

        binding.pageViewPost.adapter = postAdapter
        binding.pageViewPost.isSaveEnabled = false
        TabLayoutMediator(binding.tabPost, binding.pageViewPost)
        { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Thông tin"
                }
                1 -> {
                    "Nguyên liệu"
                }
                2 -> {
                    "Cách làm"
                }
                else -> {
                    "Bình luận"
                }
            }
        }.attach()

        binding.backPost.setOnClickListener()
        {
            finish()
        }

        binding.more.setOnClickListener()
        {
            showDialog()
        }

//        binding.favoritePost.setOnClickListener()
//        {
//            viewModel.updateFavourite()
//        }

        binding.favorite.setOnClickListener()
        {
            viewModel.updateFavourite()
        }

        binding.unfavorite.setOnClickListener()
        {
            viewModel.updateFavourite()
        }

        binding.share.setOnClickListener()
        {
            FirebaseFirestore.getInstance()
                .collection("post")
                .document(viewModel.post.value!!.id)
                .update("share", viewModel.post.value!!.share + 1)
                .addOnSuccessListener {
                    var text = ""
                    viewModel.post.observe(this)
                    { post ->
                        viewModel.user.observe(this)
                        { user ->
                            text += "Tác giả: ${user.name} \nTên món ăn: ${post.nameFood} \nThời gian: ${post.cookingTime} \nĐộ khó: ${post.level}\nNguyên liệu: \n"
                            var count = 0
                            post.ingredients.forEach()
                            {
                                count++
                                text += "$count. $it\n"
                            }
                            text += "Cách làm:\n"
                            count = 0
                            post.methods.forEach()
                            {
                                count++
                                text += "Bước $count: $it\n"
                            }
                            text += "Các hình ảnh món ăn:\n"
                            post.images.forEach()
                            {
                                text += "$it\n\n"
                            }
                            text += "Ứng dụng được hoàn thiện bởi:\nTrần Ngọc Tiến\nTrần Trọng Hoàng \nBùi Lê Hoài An"
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, text)
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)
                        }
                    }
                }
        }

        viewModel.post.observe(this) {
            if (FirebaseAuth.getInstance().currentUser?.email.toString().compareTo(it.owner) == 0)
                binding.more.visibility = View.VISIBLE
            else binding.more.visibility = View.GONE

            if (it.favourites.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString()) > -1) {
                binding.favorite.visibility = View.VISIBLE
                binding.unfavorite.visibility = View.GONE
//                binding.favoritePost.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null))
            }
            else {
                binding.favorite.visibility = View.GONE
                binding.unfavorite.visibility = View.VISIBLE
//                binding.favoritePost.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_border, null))
            }

//            slider images
            it.images.let { list ->
                viewPagerAdapter = ImageSlideAdapter(this, list )
                binding.viewpager.adapter = viewPagerAdapter
                indicator = binding.indicator
                indicator.setViewPager(binding.viewpager)
            }
        }
    }

    private fun showDialog()
    {
        val dialog = Dialog(this)
        val dialogBinding: LayoutDeleteBinding = LayoutDeleteBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.delete.setOnClickListener()
        {
            viewModel.post.value?.favourites?.forEach()
            { username ->
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(username)
                    .get()
                    .addOnSuccessListener { data ->
                        val user = User()
                        user.getData(data)
                        val list = user.favourites
                        list.remove(id)
                        FirebaseFirestore.getInstance()
                            .collection("user")
                            .document(username)
                            .update("favourites", list)
                    }
            }
            val storageRef = FirebaseStorage.getInstance().reference
            viewModel.post.value?.images?.forEach()
            { url ->
                val httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url)
                storageRef.child("post/${httpsReference.name}").delete()
            }
            FirebaseFirestore.getInstance()
                .collection("post")
                .document(id)
                .delete()
            val postList = viewModel.myData.value?.post
            postList?.remove(id)
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(FirebaseAuth.getInstance().currentUser?.email.toString())
                .update("post", postList)

            finish()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.show()
    }
}