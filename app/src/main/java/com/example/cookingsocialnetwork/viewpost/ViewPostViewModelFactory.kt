package com.example.cookingsocialnetwork.viewpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewPostViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  ViewPostViewModel() as T
    }
}