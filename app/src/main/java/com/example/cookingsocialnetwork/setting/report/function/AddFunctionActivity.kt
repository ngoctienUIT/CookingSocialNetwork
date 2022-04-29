package com.example.cookingsocialnetwork.setting.report.function

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.databinding.ActivityAddFunctionBinding

class AddFunctionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddFunctionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFunctionBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.backAddFunction.setOnClickListener()
        {
            finish()
        }

        binding.send.setOnClickListener()
        {

        }
    }
}