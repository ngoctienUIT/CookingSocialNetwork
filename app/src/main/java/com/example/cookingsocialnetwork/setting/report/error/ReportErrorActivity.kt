package com.example.cookingsocialnetwork.setting.report.error

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.databinding.ActivityReportErrorBinding

class ReportErrorActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportErrorBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.backReportError.setOnClickListener()
        {
            finish()
        }

        binding.send.setOnClickListener()
        {

        }
    }
}