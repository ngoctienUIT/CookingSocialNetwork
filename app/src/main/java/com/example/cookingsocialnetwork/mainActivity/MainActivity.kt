package com.example.cookingsocialnetwork.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.cookingsocialnetwork.model.LanguageManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.intro.IntroPage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        window.statusBarColor = ContextCompat.getColor(this, R.color.intro)
    }

    override fun onStart() {
        super.onStart()
        val sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
        val check = sharePref.getString("language", "vi")
        val lang = LanguageManager(this)
        when (check)
        {
            "vi" -> lang.updateResource("vi")
            "en" -> lang.updateResource("en-US")
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val introPage = Intent(this, IntroPage::class.java)
            startActivity(introPage)
            finish()
        },3000)
    }
}