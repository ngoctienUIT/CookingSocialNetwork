package com.example.cookingsocialnetwork.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime

class AccountViewModel():ViewModel() {
    private lateinit var user: User

    init {
        //Khởi tạo dữ liệu cho user
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                documents ->
                for (document in documents)
                {
                    if (document.id.toString() == "infor")
                    {
                        user.getData(document)
                    }
                    else if (document.id.toString() == "favourites")
                    {
                        user.favourites = document.data["favourites"] as MutableList<String>
                    }
                    else if (document.id.toString() == "followers")
                    {
                        user.followers = document.data["followers"] as MutableList<String>
                    }
                    else if (document.id.toString() == "following")
                    {
                        user.following = document.data["following"] as MutableList<String>
                    }
                    else if (document.id.toString() == "post")
                    {
                        user.post = document.data["post"] as MutableList<String>
                    }
                }
            }


        /*FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: ${snapshot.data}")
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }*/
    }

    var getName: String = user.name
    var getAvatar: String = user.avatar
    var getUsername: String = user.username
    var getGender: String = user.gender
    var getBirthday: DateTime = user.birthday
    var getDescription: String = user.description
}