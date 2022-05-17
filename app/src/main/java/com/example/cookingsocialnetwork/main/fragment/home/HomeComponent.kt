package com.example.cookingsocialnetwork.main.fragment.home

import com.google.android.datatransport.runtime.dagger.Component
import javax.inject.Singleton

@dagger.Component(modules = [HomeViewModule::class])
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}