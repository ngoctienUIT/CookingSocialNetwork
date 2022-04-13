package com.example.cookingsocialnetwork.main.fragment.search.view.postSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostSearchFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostSearchViewModel() as T
    }
}