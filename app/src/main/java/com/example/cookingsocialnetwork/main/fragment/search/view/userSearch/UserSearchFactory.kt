package com.example.cookingsocialnetwork.main.fragment.search.view.userSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserSearchFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserSearchViewModel() as T
    }
}