package com.example.cookingsocialnetwork.setting.changeProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivitySettingChangeProfileBinding

class SettingChangeProfile : AppCompatActivity() {
    private lateinit var viewModel: SettingChangeProfileViewModel
    private lateinit var databinding: ActivitySettingChangeProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_change_profile)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_change_profile)
        val factory = SettingChangeProfileViewModelFactory()

        viewModel = ViewModelProviders.of(this,factory).get(SettingChangeProfileViewModel::class.java)
        databinding.viewModel = viewModel
        databinding.lifecycleOwner = this
    }
}