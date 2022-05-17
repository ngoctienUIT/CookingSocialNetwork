package com.example.cookingsocialnetwork.main.fragment.home

import androidx.lifecycle.ViewModelProvider
import com.google.android.datatransport.runtime.dagger.Binds
import com.google.android.datatransport.runtime.dagger.Module
import dagger.Provides

@dagger.Module
abstract class HomeViewModule {
    @dagger.Binds
    abstract fun bindViewModelFactory(factory: HomeViewModelFactory): ViewModelProvider.Factory
}