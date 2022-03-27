package com.example.cookingsocialnetwork.setting.changeProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingChangeProfileViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingChangeProfileViewModel() as T
    }
}