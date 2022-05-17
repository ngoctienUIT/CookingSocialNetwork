package com.example.cookingsocialnetwork.post.loadPost

data class Post(val imageResource : Int, val name: String, val description: String, val serves: String, val cookTime: String, val ratingLevel: Int, val ingredient: List<String>, val method: List<String>)
