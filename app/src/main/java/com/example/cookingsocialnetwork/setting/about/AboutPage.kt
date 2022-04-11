package com.example.cookingsocialnetwork.setting.about

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingsocialnetwork.R
import kotlinx.android.synthetic.main.activity_about_page.*

class AboutPage : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)
        supportActionBar?.hide()
        val pInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        version.text = (getString(R.string.version) +" "+ pInfo.versionName)

        back_info.setOnClickListener()
        {
            finish()
        }
    }
}