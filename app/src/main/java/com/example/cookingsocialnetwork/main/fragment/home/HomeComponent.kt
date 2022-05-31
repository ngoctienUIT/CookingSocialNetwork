package com.example.cookingsocialnetwork.main.fragment.home

@dagger.Component(modules = [HomeViewModule::class])
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}