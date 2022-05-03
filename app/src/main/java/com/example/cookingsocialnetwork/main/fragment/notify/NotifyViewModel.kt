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
                    val notifyData = data?.get("notify") as Map<String, Any>
                    val favorite = notifyData["favorite"] as MutableList<Map<String, Any>>
                    for (i in favorite) {
                        val notify = Notify(i["name"].toString(), LocalDateTime.now())
                        _notifys.add(notify)
                    }
                    notifys.value = _notifys

                    _comments = mutableListOf()
                    val comment = notifyData["comment"] as MutableList<Map<String, Any>>
                    for (i in comment) {
                        val notify = Notify(i["name"].toString(), LocalDateTime.now())
                        _comments.add(notify)
                    }
                    comments.value = _comments

                    val follow = notifyData["follow"] as MutableList<Map<String, Any>>
                    for (i in comment) {
                        val notify = Notify(i["name"].toString(), LocalDateTime.now())
//                        _notifys.add(notify)
                    }
//                    notifys.value = _notifys
                }
            }
    }
}