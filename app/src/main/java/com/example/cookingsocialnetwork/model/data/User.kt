package com.example.cookingsocialnetwork.model.data

import com.google.firebase.firestore.DocumentSnapshot

data class User(
    var name: String,
    var avatar: String,
    var username: String,
    var gender: String,
    var birthday: String,
    var description: String,
    var followers: MutableList<String>,
    var following: MutableList<String>,
    var favourites: MutableList<String>,
    var post: MutableList<String>)
{
    constructor(): this("Name", "Avatar", "@username", "None", "None", "None",
        mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf()) {}

    fun getData(document: DocumentSnapshot) {
        val data = document.data
        val info = data?.get("info") as Map<String, Any>
        name = info["name"].toString()
        avatar = info["avatar"].toString()
        gender = info["gender"].toString()
        username = info["username"].toString()
        description = info["description"].toString()
        birthday = info["birthday"].toString()
        favourites = data["favourites"] as MutableList<String>
        followers = data["followers"] as MutableList<String>
        following = data["following"] as MutableList<String>
        post = data["post"] as MutableList<String>
    }
}