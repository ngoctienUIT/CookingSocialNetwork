package com.example.cookingsocialnetwork.main.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.User
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
        firestore.collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener()
            {
                    snapshot,e ->
                if (e!=null)
                {
                    return@addSnapshotListener
                }

                if (snapshot!= null)
                {
                    val documents = snapshot.documents
                    documents.forEach()
                    {
                        user.getData(it)
                    }
                    _user.value = user
                }
            }
    }
}