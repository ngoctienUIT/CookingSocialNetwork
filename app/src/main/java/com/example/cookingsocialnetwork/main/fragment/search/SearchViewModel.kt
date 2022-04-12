package com.example.cookingsocialnetwork.main.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class SearchViewModel: ViewModel() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var posts:MutableList<Post> = mutableListOf()
    private var _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
//        listenToDataPost()
    }

    private fun listenToDataPost()
    {
        firestore.collection("post")
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
                    posts = mutableListOf()
                    documents.forEach()
                    {
                        val post = Post()
                        post.getData(it)
                        posts.add(post)
                    }
                    _posts.value = posts
                }
            }
    }

    var getPosts: MutableLiveData<MutableList<Post>>
        get() {
            return _posts
        }
        set(value) {
            _posts = value
        }
}