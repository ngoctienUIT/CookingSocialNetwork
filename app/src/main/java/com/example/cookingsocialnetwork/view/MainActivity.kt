package com.example.cookingsocialnetwork.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser == null)
        {
            val splashPage = Intent(this, SplashPage::class.java)
            startActivity(splashPage)
        }
        else
        {
            val homePage = Intent(this, HomePage::class.java)
            startActivity(homePage)
        }
    }
}