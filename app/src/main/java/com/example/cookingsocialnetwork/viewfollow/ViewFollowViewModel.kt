package com.example.cookingsocialnetwork.viewfollow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ViewFollowViewModel: ViewModel() {
    var userName: String = ""
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _user: User = User()
    var user: MutableLiveData<User> = MutableLiveData()
    private var _myData: User = User()
    var myData: MutableLiveData<User> = MutableLiveData()
    private var _following: MutableList<User> = mutableListOf()
    var following: MutableLiveData<MutableList<User>> = MutableLiveData()
    private var _follower: MutableList<User> = mutableListOf()
    var follower: MutableLiveData<MutableList<User>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        user.value = _user
        following.value = _following
        follower.value = _follower
        getMyData()
    }

    private fun getMyData() {
        firestore
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _myData.getData(snapshot)
                    myData.value = _myData
                }
            }
    }

    fun getData() {
        firestore
            .collection("user")
            .document(userName)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _following = mutableListOf()
                    _follower = mutableListOf()
                    _user.getData(snapshot)
                    user.value = _user

                    getDataFollowing(_user.following)
                    getDataFollower(_user.followers)
                }
            }
    }

    private fun getDataFollowing(listFollowing: MutableList<String>) {
        listFollowing.forEach()
        {
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(it)
                .get()
                .addOnSuccessListener { userDoc ->
                    val userFollowing = User()
                    userFollowing.getData(userDoc)
                    _following.add(userFollowing)
                    following.value = _following
                }
        }
    }

    private fun getDataFollower(listFollower: MutableList<String>) {
        listFollower.forEach()
        {
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(it)
                .get()
                .addOnSuccessListener { userDoc ->
                    val userFollower = User()
                    userFollower.getData(userDoc)
                    _follower.add(userFollower)
                    follower.value = _follower
                }
        }
    }
}