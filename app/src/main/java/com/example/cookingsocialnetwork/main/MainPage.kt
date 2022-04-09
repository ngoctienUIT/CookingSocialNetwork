package com.example.cookingsocialnetwork.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.main.fragment.home.HomeFragment
import com.example.cookingsocialnetwork.main.fragment.notify.NotificationsFragment
import com.example.cookingsocialnetwork.main.fragment.profile.ProfileFragment
import com.example.cookingsocialnetwork.main.fragment.search.SearchFragment
import com.example.cookingsocialnetwork.post.PostPage
import com.example.cookingsocialnetwork.setting.SettingPage
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlin.system.exitProcess

class MainPage : AppCompatActivity() {
    private val home = HomeFragment()
    private val profile = ProfileFragment()
    private val search = SearchFragment()
    private val notify = NotificationsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        supportActionBar?.hide()
        bottom_navigation.background = null
        bottom_navigation.menu.getItem(2).isEnabled = false

        replaceFragment(home)
        bottom_navigation.setOnItemSelectedListener {
            item ->
            when (item.itemId)
            {
                R.id.ic_home -> replaceFragment(home)
                R.id.ic_search -> replaceFragment(search)
                R.id.ic_notifications -> replaceFragment(notify)
                R.id.ic_person -> replaceFragment(profile)
            }
            true
        }

        floatingButton.setOnClickListener()
        {
            val post = Intent(this, PostPage::class.java)
            startActivity(post)
        }
    }

    private fun replaceFragment(fragment: Fragment)
    {
        Log.w("Move",fragment.toString())
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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}