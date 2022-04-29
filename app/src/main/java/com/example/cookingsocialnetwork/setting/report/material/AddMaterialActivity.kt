package com.example.cookingsocialnetwork.setting.report.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.databinding.ActivityAddMaterialBinding

class AddMaterialActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMaterialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMaterialBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.backAddMaterial.setOnClickListener()
        {
            finish()
        }

        binding.send.setOnClickListener()
        {

        }
    }
}