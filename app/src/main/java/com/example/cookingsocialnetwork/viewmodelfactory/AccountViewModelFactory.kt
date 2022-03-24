package com.example.cookingsocialnetwork.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.viewmodel.AccountViewModel


class AccountViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel() as T
    }
}