package com.example.cookingsocialnetwork.main.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class SearchViewModel: ViewModel() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var history: MutableList<String>
    var searchHistory: MutableLiveData<MutableList<String>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToDataSearchHistory()
    }

    private fun listenToDataSearchHistory()
    {
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser.toString())
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) return@addSnapshotListener

                if (snapshot != null) {
                    history = mutableListOf()
//                    history = snapshot.data?.get("searchHistory") as MutableList<String>
                    searchHistory.value = history
                }
            }
    }
}