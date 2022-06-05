package com.example.cookingsocialnetwork.main.fragment.profile.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class PostViewModel: ViewModel() {
    var listPost: MutableList<String> = mutableListOf()
    private var post: MutableList<Post> = mutableListOf()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postData: MutableLiveData<MutableList<Post>> = MutableLiveData()

    var getPost: MutableLiveData<MutableList<Post>>
        get() {
            return postData
        }
        set(value) {
            postData = value
        }

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToData()
    }

    fun listenToData()
    {
        post = mutableListOf()
        listPost.forEach()
        { item ->
            firestore.collection("post")
                .document(item)
                .addSnapshotListener()
                { snapshot, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    if (snapshot!= null && snapshot.exists())
                    {
                        val myPost = Post()
                        myPost.getData(snapshot)
                        post.add(myPost)
                        postData.value = post
                    }
                }
        }
    }
}