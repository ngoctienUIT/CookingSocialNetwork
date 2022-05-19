package com.example.cookingsocialnetwork.splash

import androidx.lifecycle.ViewModel

class SplashViewModel:ViewModel() {
    lateinit var id: String
    lateinit var img: String
    lateinit var nameFood: String
    lateinit var description: String
    var ration: Int = 0
    var cookingTime: Float = 0.0f
    lateinit var ingredients: MutableList<String>
    lateinit var making: MutableList<String>

    private fun initID(){

    }
}