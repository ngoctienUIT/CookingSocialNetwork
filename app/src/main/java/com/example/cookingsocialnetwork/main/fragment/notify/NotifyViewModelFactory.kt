package com.example.cookingsocialnetwork.main.fragment.notify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotifyViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotifyViewModel() as T
    }
}