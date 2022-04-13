package com.example.cookingsocialnetwork.main.fragment.search.view.userSearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class UserSearchViewModel: ViewModel() {
    var query: String = ""
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var users: MutableList<User>
    var _users: MutableLiveData<MutableList<User>> = MutableLiveData()
    var myData: MutableLiveData<User> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToDataUser()
//        listenMyData()
    }

    private fun listenMyData()
    {
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) return@addSnapshotListener
                if (snapshot!=null)
                {
                    val user = User()
                    user.getData(snapshot)
                    myData.value = user
                }
            }
    }

    private fun listenToDataUser()
    {
        firestore.collection("user")
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) return@addSnapshotListener

                if (snapshot != null) {
                    val documents = snapshot.documents
                    users = mutableListOf()
                    documents.forEach()
                    {
                        val data = it.data?.get("info") as Map<String, Any>

                        if (data["name"].toString().uppercase().contains(query.uppercase())) {
                            val user = User()
                            user.getData(it)
                            if (user.username.compareTo(FirebaseAuth.getInstance().currentUser?.email.toString()) == 0)
                                myData.value = user
                            users.add(user)
                        }
                    }
                    _users.value = users
                }
            }
    }
}