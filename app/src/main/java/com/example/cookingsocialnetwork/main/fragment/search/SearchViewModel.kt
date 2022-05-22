package com.example.cookingsocialnetwork.main.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class SearchViewModel: ViewModel() {
    var query: String = ""
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    //data history
    lateinit var history: MutableList<String>
    var searchHistory: MutableLiveData<MutableList<String>> = MutableLiveData()

    //data user
    lateinit var users: MutableList<User>
    var _users: MutableLiveData<MutableList<User>> = MutableLiveData()
    var myData: MutableLiveData<User> = MutableLiveData()

    //data post
    lateinit var posts: MutableList<Post>
    var _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToDataSearchHistory()
        listenToDataUser()
//        listenToDataPost()
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

    fun listenToDataUser()
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
                        if (data["username"].toString().compareTo(FirebaseAuth.getInstance().currentUser?.email.toString()) == 0) {
                            val user = User()
                            user.getData(it)
                            myData.value = user
                        }
                        if (data["name"].toString().uppercase().contains(query.uppercase())
                            || data["username"].toString().uppercase().contains(query.uppercase())) {
                            val user = User()
                            user.getData(it)
                            users.add(user)
                        }
                    }
                    _users.value = users
                }
            }
    }

    private fun listenToDataPost()
    {
        firestore.collection("post")
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) return@addSnapshotListener

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