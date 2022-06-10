package com.example.cookingsocialnetwork.model

import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FollowControl {
    companion object {
        fun follow(username: String) {
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(FirebaseAuth.getInstance().currentUser?.email.toString())
                .get()
                .addOnSuccessListener { myUser ->
                    val myData = User()
                    myData.getData(myUser)

                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(username)
                        .get()
                        .addOnSuccessListener { dataUser ->
                            val userData = User()
                            userData.getData(dataUser)

                            val following = myData.following
                            val follower = userData.followers

                            val checkFollow = myData.following.indexOf(username) != -1

                            if (!checkFollow) {
                                following.add(userData.username)
                                follower.add(myData.username)
                                NotifyControl.addNotify(userData.username, "", "", "follow")
                            } else {
                                following.remove(userData.username)
                                follower.remove(myData.username)
                                NotifyControl.removeNotify(userData.username, "","","follow")
                            }

                            FirebaseFirestore.getInstance()
                                .collection("user")
                                .document(myData.username)
                                .update("following", following)

                            FirebaseFirestore.getInstance()
                                .collection("user")
                                .document(username)
                                .update("followers", follower)

                        }
                }
        }
    }
}