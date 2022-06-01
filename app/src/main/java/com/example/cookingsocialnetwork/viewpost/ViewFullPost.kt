package com.example.cookingsocialnetwork.viewpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityViewFullPostBinding
import com.example.cookingsocialnetwork.viewpost.adapter.IngredientAdapter
import com.example.cookingsocialnetwork.viewpost.adapter.MethodsAdapter
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator

class ViewFullPost : AppCompatActivity() {
    lateinit var binding: ActivityViewFullPostBinding
    private lateinit var viewModel: ViewFullPostViewModel
    private lateinit var id: String
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator

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

        viewModel.user.observe(this)
        {
            Picasso.get().load(it.avatar).into(binding.avatar)
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

            //slider images
            it.images.let { list ->
                viewPagerAdapter = ImageSlideAdapter(this, list )
                binding.viewpager.adapter = viewPagerAdapter
                indicator = binding.indicator
                indicator.setViewPager(binding.viewpager)

            }

            val ingredientAdapter = ListIngredientAdapter(this, it.ingredients)
            binding.ingredients.isClickable = false
            binding.ingredients.adapter = ingredientAdapter
            binding.methods.adapter = ingredientAdapter
        }

        viewModel.checkFavourite().observe(this)
        {
            if (it) binding.icoFavourite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null))
            else binding.icoFavourite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null))
        }

        binding.btnFavourite.setOnClickListener()
        {
            viewModel.updateFavourite()
        }
    }
}