package com.example.cookingsocialnetwork.model

import androidx.databinding.BaseObservable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.type.DateTime

data class User(
    var name: String,
    var avatar: String,
    var username: String,
    var gender: String,
    var birthday: DateTime,
    var description: String,
    var followers: MutableList<String>,
    var following: MutableList<String>,
    var favourites: MutableList<String>,
    var post: MutableList<String>): BaseObservable()
{
        fun getData(documentSnapshot: DocumentSnapshot)
        {
            val data = documentSnapshot.data
            name = data?.get("name").toString()
            avatar = data?.get("avatar").toString()
            gender = data?.get("gender").toString()
            username = data?.get("username").toString()
            description = data?.get("description").toString()
            birthday = data?.get("birthday") as DateTime
        }
}