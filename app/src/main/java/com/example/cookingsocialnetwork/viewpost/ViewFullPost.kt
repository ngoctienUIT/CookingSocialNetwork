package com.example.cookingsocialnetwork.viewpost

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityViewFullPostBinding
import com.example.cookingsocialnetwork.profile.ProfileActivity
import com.example.cookingsocialnetwork.viewpost.adapter.CommentAdapter
import com.example.cookingsocialnetwork.viewpost.adapter.ImageSlideAdapter
import com.example.cookingsocialnetwork.viewpost.adapter.IngredientAdapter
import com.example.cookingsocialnetwork.viewpost.adapter.MethodsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator


class ViewFullPost : AppCompatActivity() {
    lateinit var binding: ActivityViewFullPostBinding
    private lateinit var viewModel: ViewFullPostViewModel
    private lateinit var id: String
    private lateinit var viewPagerAdapter: ImageSlideAdapter
    private lateinit var indicator: CircleIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("id_post")!!
        binding = ActivityViewFullPostBinding.inflate(layoutInflater)
        val factory = ViewFullPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewFullPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        supportActionBar?.hide()

        binding.backViewFullPost.setOnClickListener()
        {
            finish()
        }

        binding.viewProfile.setOnClickListener()
        {
            val profile = Intent(this, ProfileActivity::class.java)
            profile.putExtra("user_name", viewModel.user.value?.username)
            startActivity(profile)
        }

        viewModel.user.observe(this)
        {
            Picasso.get().load(it.avatar).into(binding.avatar)
        }

        viewModel.myData.observe(this)
        {
            Picasso.get().load(it.avatar).into(binding.userAvatar)
        }

        viewModel.post.observe(this) {
            val ingredientAdapter = IngredientAdapter(it.ingredients)
            val ingredientsLayoutManager = LinearLayoutManager(this)
            binding.ingredients.layoutManager = ingredientsLayoutManager
            binding.ingredients.adapter = ingredientAdapter

            val methodsAdapter = MethodsAdapter(it.ingredients)
            val methodsLayoutManager = LinearLayoutManager(this)
            binding.methods.layoutManager = methodsLayoutManager
            binding.methods.adapter = methodsAdapter

            val commentAdapter = CommentAdapter(it.comments, id)
            val commentLayoutManager = LinearLayoutManager(this)
            binding.comments.layoutManager = commentLayoutManager
            binding.comments.adapter = commentAdapter

            if (it.favourites.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString()) > -1)
                binding.icoFavourite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null))
            else binding.icoFavourite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_border, null))

//            slider images
            it.images.let { list ->
                viewPagerAdapter = ImageSlideAdapter(this, list )
                binding.viewpager.adapter = viewPagerAdapter
                indicator = binding.indicator
                indicator.setViewPager(binding.viewpager)
            }
        }
        binding.send.visibility = View.GONE
        binding.contentComment.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() == "") binding.send.visibility = View.GONE
                else binding.send.visibility = View.VISIBLE
            }

        })

        binding.send.setOnClickListener()
        {
            viewModel.updateComment(binding.contentComment.text.toString())
            binding.contentComment.text = Editable.Factory.getInstance().newEditable("")
        }

        binding.btnFavourite.setOnClickListener()
        {
            viewModel.updateFavourite()
        }

        binding.btnShare.setOnClickListener()
        {
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

        binding.comments.visibility = View.GONE
        binding.writeComment.visibility = View.GONE
        binding.btnComment.setOnClickListener()
        {
            if (binding.comments.visibility == View.GONE) {
                binding.comments.visibility = View.VISIBLE
                binding.writeComment.visibility = View.VISIBLE
            }
            else {
                binding.comments.visibility = View.GONE
                binding.writeComment.visibility = View.GONE
            }
        }
    }
}