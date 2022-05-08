package com.example.cookingsocialnetwork.main.fragment.notify

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.Notify
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.time.LocalDateTime

class NotifyViewModel: ViewModel() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _notifys: MutableList<Notify> = mutableListOf()
    var notifys: MutableLiveData<MutableList<Notify>> = MutableLiveData()
    private var _comments: MutableList<Notify> = mutableListOf()
    var comments: MutableLiveData<MutableList<Notify>> = MutableLiveData()
    private var _favorites: MutableList<Notify> = mutableListOf()
    var favorites: MutableLiveData<MutableList<Notify>> = MutableLiveData()
    private var _follows: MutableList<Notify> = mutableListOf()
    var follows: MutableLiveData<MutableList<Notify>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToNotify()
    }

    private fun listenToNotify()
    {
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _notifys = mutableListOf()
                    val data = snapshot.data
                    val notifyData = data?.get("notify") as MutableList<Map<String, Any>>

                    for (item in notifyData) {
                        val notify = Notify(
                            item["name"].toString(),
                            item["type"].toString(),
                            item["status"] as Long,
                            LocalDateTime.now()
                        )
                        _notifys.add(notify)
                        when (notify.type) {
                            "follow" -> _follows.add(notify)
                            "comment" -> _comments.add(notify)
                            else -> _favorites.add(notify)
                        }
                    }

                    notifys.value = _notifys
                    comments.value = _comments
                    favorites.value = _favorites
                    follows.value = _follows
                }
            }
    }
}