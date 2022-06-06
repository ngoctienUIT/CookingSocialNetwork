package com.example.cookingsocialnetwork.viewfollow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewFollowViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  ViewFollowViewModel() as T
    }
}