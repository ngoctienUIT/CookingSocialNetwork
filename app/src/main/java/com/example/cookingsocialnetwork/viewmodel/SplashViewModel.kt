package com.example.cookingsocialnetwork.viewmodel

import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashViewModel:ViewModel() {
    fun getData()
    {
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor")
            .get()
            .addOnSuccessListener {
                    documentSnapshot  -> Log.w("MainActivity.TAG", documentSnapshot.data?.get("name").toString())

            }

        /*FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")
                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }*/
    }
}