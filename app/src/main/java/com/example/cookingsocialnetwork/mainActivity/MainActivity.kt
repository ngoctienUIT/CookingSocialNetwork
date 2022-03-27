package com.example.cookingsocialnetwork.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.home.HomePage
import com.example.cookingsocialnetwork.splash.SplashPage
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseAuth.getInstance().currentUser == null) {
                val splashPage = Intent(this, SplashPage::class.java)
                startActivity(splashPage)
            } else {
                val homePage = Intent(this, HomePage::class.java)
                startActivity(homePage)
            }
            finish()
        },3000)
    }
}