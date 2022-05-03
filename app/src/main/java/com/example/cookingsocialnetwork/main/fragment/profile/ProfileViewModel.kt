package com.example.cookingsocialnetwork.main.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ProfileViewModel: ViewModel() {

    private var user: User = User()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _user: MutableLiveData<User> = MutableLiveData()

    var getUser: MutableLiveData<User>
        get() {
            return _user
        }
        set(value) {
            _user = value
        }

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToData()
    }

    private fun listenToData()
    {
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
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
}