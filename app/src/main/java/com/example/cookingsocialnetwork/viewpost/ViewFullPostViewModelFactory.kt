package com.example.cookingsocialnetwork.viewpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewFullPostViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  ViewFullPostViewModel() as T
    }
}