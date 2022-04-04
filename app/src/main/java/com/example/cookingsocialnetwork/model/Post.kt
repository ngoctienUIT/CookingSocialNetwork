package com.example.cookingsocialnetwork.model

import com.google.firebase.firestore.DocumentSnapshot

data class Post(
    var id: String,
    var description: String,
    var duration: String,
    var favourites: MutableList<String>,
    var images: MutableList<String>,
    var comments: MutableList<Map<String, Any>>,
    var ingredients: MutableList<Map<String, Any>>,
    var making: String,
    var name: String,
    var owner: String,
    var rate: Map<String, MutableList<String>>,
    var share: Int
) {
    constructor() : this(
        "", "", "", mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf(), "", "", "", mapOf(), 0
    ) {

    }

    fun getData(document: DocumentSnapshot) {
        if (document.id.toString() != "count") {
            description = document.data?.get("description") as String
            duration = document.data?.get("duration") as String
            making = document.data?.get("making") as String
            name = document.data?.get("name") as String
            owner = document.data?.get("owner") as String
            share = document.data?.get("share") as Int
            favourites = document.data?.get("favourites") as MutableList<String>
            images = document.data?.get("images") as MutableList<String>
            rate = document.data?.get("rate") as Map<String, MutableList<String>>
            ingredients = document.data?.get("ingredients") as MutableList<Map<String, Any>>
            comments = document.data?.get("comments") as MutableList<Map<String, Any>>
        }
    }
}