package com.example.cookingsocialnetwork.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.home.HomePage
import com.example.cookingsocialnetwork.splash.SplashPage
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
            // set the new task and clear flags
            splashPage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(splashPage)
        }
        else
        {
            val homePage = Intent(this, HomePage::class.java)
            homePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(homePage)
        }
    }
}