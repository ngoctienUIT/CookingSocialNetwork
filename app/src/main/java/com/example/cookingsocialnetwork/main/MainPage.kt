package com.example.cookingsocialnetwork.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityMainPageBinding
import com.example.cookingsocialnetwork.main.fragment.home.HomeFragment
import com.example.cookingsocialnetwork.main.fragment.notify.NotificationsFragment
import com.example.cookingsocialnetwork.main.fragment.profile.ProfileFragment
import com.example.cookingsocialnetwork.main.fragment.search.SearchFragment
import com.example.cookingsocialnetwork.post.PostPage
import com.example.cookingsocialnetwork.post.PostPage2
import kotlin.system.exitProcess

class MainPage : AppCompatActivity() {
    private val home = HomeFragment()
    private val profile = ProfileFragment()
    private val notify = NotificationsFragment()
    private lateinit var binding: ActivityMainPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(2).isEnabled = false

        replaceFragment(home)
        binding.bottomNavigation.setOnItemSelectedListener {
            item ->
            when (item.itemId)
            {
                R.id.ic_home -> replaceFragment(home)
                R.id.ic_search -> replaceFragment(SearchFragment())
                R.id.ic_notifications -> replaceFragment(notify)
                R.id.ic_person -> replaceFragment(profile)
            }
            true
        }

        binding.floatingButton.setOnClickListener()
        {
            //val post = Intent(this, PostPage::class.java)
            val post = Intent(this, PostPage2::class.java)
            startActivity(post)
        }

    }

    private fun replaceFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
            exitProcess(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.double_back), Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}