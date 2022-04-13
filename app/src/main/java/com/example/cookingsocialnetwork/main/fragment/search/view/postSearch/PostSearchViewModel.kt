package com.example.cookingsocialnetwork.main.fragment.search.view.postSearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class PostSearchViewModel: ViewModel() {
    var query: String = ""
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var posts: MutableList<Post>
    var _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
//        listenToDataUser()
    }

    private fun listenToDataUser()
    {
        firestore.collection("post")
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val documents = snapshot.documents
                    posts = mutableListOf()
                    documents.forEach()
                    {
                        val data = it.data?.get("info") as Map<String, Any>
                        if (data["name"].toString().uppercase().contains(query.uppercase())) {
                            val post = Post()
                            post.getData(it)
                            posts.add(post)
                        }
                    }
                    _posts.value = posts
                }
            }
    }
}