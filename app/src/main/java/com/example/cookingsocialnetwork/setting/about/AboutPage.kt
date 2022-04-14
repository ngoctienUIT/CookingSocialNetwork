package com.example.cookingsocialnetwork.setting.about

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityAboutPageBinding

class AboutPage : AppCompatActivity() {
    private lateinit var binding: ActivityAboutPageBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val pInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        binding.version.text = (getString(R.string.version) +" "+ pInfo.versionName)

        binding.backInfo.setOnClickListener()
        {
            finish()
        }
    }
}