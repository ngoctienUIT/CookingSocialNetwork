package com.example.cookingsocialnetwork.main.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class SearchViewModel: ViewModel() {
    var query: String = ""
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var history: MutableList<String>
    var searchHistory: MutableLiveData<MutableList<String>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToDataSearchHistory()
    }

    fun listenToDataSearchHistory()
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
                    history = snapshot.data?.get("searchHistory") as MutableList<String>
                    val listQueryHistory = mutableListOf<String>()
                    for (search in history)
                        if (search.uppercase().contains(query.uppercase())) listQueryHistory.add(search)
                    searchHistory.value = listQueryHistory
                }
            }
    }
}