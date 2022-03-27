package com.example.cookingsocialnetwork.model

import android.annotation.SuppressLint
import com.google.firebase.firestore.DocumentSnapshot
import java.text.SimpleDateFormat

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
        mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf()) {

    }

    fun getData(document: DocumentSnapshot)
    {
        if (document.id.toString() == "infor") {
            getInfor(document)
        } else if (document.id.toString() == "favourites") {
            favourites = document.data?.get("favourites") as MutableList<String>
        } else if (document.id.toString() == "followers") {
            followers = document.data?.get("followers") as MutableList<String>
        } else if (document.id.toString() == "following") {
            following = document.data?.get("following") as MutableList<String>
        } else if (document.id.toString() == "post") {
            post = document.data?.get("post") as MutableList<String>
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getInfor(document : DocumentSnapshot) {
            val data = document.data
            name = data?.get("name").toString()
            avatar = data?.get("avatar").toString()
            gender = data?.get("gender").toString()
            username = data?.get("username").toString()
            description = data?.get("description").toString()
            val time = document.getDate("birthday")
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            birthday = sdf.format(time)
        }
}