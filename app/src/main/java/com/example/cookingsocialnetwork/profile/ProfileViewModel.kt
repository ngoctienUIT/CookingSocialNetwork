package com.example.cookingsocialnetwork.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.NotifyControl
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ProfileViewModel: ViewModel() {
    private var user: User = User()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _user: MutableLiveData<User> = MutableLiveData()
    var userName: String = ""

    var getUser: MutableLiveData<User>
        get() {
            return _user
        }
        set(value) {
            _user = value
        }

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
//        listenToData()
    }

    fun listenToData()
    {
        firestore.collection("user")
            .document(userName)
            .addSnapshotListener()
            { snapshot,e ->
                if (e!=null)
                {
                    return@addSnapshotListener
                }

                if (snapshot!= null && snapshot.exists())
                {
                    user.getData(snapshot)
                    _user.value = user
                }
            }
    }

    fun eventFollow()
    {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                val myData = User()
                myData.getData(it)
                val following = myData.following
                val follower = _user.value!!.followers
                val checkFollow = user.followers.indexOf(FirebaseAuth.getInstance().currentUser?.email) != -1
                if (!checkFollow) {
                    following.add(_user.value!!.username)
                    follower.add(myData.username)
                    NotifyControl.addNotify(user.username, "", "", "follow")
                } else {
                    following.remove(_user.value!!.username)
                    follower.remove(myData.username)
                    NotifyControl.removeNotify(user.username, "", "", "follow")
                }
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(myData.username)
                    .update("following", following)

                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(_user.value!!.username)
                    .update("followers", follower)
            }
    }
}