package com.example.cookingsocialnetwork.setting.report

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookingsocialnetwork.databinding.ActivityReportBinding
import com.example.cookingsocialnetwork.setting.report.view.ReportErrorActivity
import com.example.cookingsocialnetwork.setting.report.view.AddFunctionActivity
import com.example.cookingsocialnetwork.setting.report.view.AddMaterialActivity

class ReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.backReport.setOnClickListener()
        {
            finish()
        }

        binding.btnReportError.setOnClickListener()
        {
            val reportErrorActivity = Intent(this, ReportErrorActivity::class.java)
            startActivity(reportErrorActivity)
        }

        binding.btnAddFunction.setOnClickListener()
        {
            val addFunction = Intent(this, AddFunctionActivity::class.java)
            startActivity(addFunction)
        }

        binding.btnAddMaterial.setOnClickListener()
        {
            val addMaterial = Intent(this, AddMaterialActivity::class.java)
            startActivity(addMaterial)
        }
    }
}