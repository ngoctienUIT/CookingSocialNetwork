package com.example.cookingsocialnetwork.main.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val myHomeViewModelProvider: javax.inject.Provider<HomeViewModel>) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return myHomeViewModelProvider.get() as T
    }

}