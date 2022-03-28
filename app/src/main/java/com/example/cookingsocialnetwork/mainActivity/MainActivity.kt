package com.example.cookingsocialnetwork.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.splash.SplashPage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            val splashPage = Intent(this, SplashPage::class.java)
            startActivity(splashPage)
            finish()
        },3000)
    }
}