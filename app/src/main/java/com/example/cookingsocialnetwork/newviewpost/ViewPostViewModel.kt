package com.example.cookingsocialnetwork.newviewpost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.NotifyControl
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.time.LocalDateTime

class ViewPostViewModel: ViewModel() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _post: Post = Post()
    var post: MutableLiveData<Post> = MutableLiveData()
    private var _user: User = User()
    var user: MutableLiveData<User> = MutableLiveData()
    private var _myData: User = User()
    var myData: MutableLiveData<User> = MutableLiveData()
    var id: String = ""

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        post.value = _post
        user.value = _user
        getMyData()
    }

    private fun getMyData()
    {
        firestore
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _myData.getData(snapshot)
                    myData.value = _myData
                }
            }
    }

    fun getData() {
        firestore
            .collection("post")
            .document(id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _post.getData(snapshot)
                    post.value = _post
                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(post.value!!.owner)
                        .get()
                        .addOnSuccessListener { userSnap ->
                            _user.getData(userSnap)
                            user.value = _user
                        }
                }
            }
    }

    //update dữ liệu sau khi nhấn thích hoặc bỏ thích
    fun updateFavourite() {
        var check = false
        val index = post.value?.favourites?.indexOf(FirebaseAuth.getInstance().currentUser?.email!!)
        if (index!! > -1) check = true
        val favouritesPost = mutableListOf<String>()
        favouritesPost.addAll(post.value!!.favourites)
        if (!check) favouritesPost.add(FirebaseAuth.getInstance().currentUser?.email.toString())
        else favouritesPost.remove(FirebaseAuth.getInstance().currentUser?.email.toString())
        FirebaseFirestore.getInstance()
            .collection("post")
            .document(post.value!!.id)
            .update("favourites", favouritesPost)

        var favouritesUser: MutableList<String>

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    favouritesUser = it.get("favourites") as MutableList<String>
                    if (!check) {
                        favouritesUser.add(post.value!!.id)
                        if (!check && _post.owner != _myData.username)
                            NotifyControl.addNotify(_user.username, "", _post.id, "favorite")
                    } else {
                        favouritesUser.remove(post.value!!.id)
                        NotifyControl.removeNotify(_user.username, "", _post.id, "favorite")
                    }
                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(FirebaseAuth.getInstance().currentUser?.email.toString())
                        .update("favourites", favouritesUser)
                }
            }
    }

    fun updateComment(content: String)
    {
        val comment = hashMapOf(
            "content" to content,
            "favourite" to mutableListOf<String>(),
            "time" to LocalDateTime.now(),
            "userName" to FirebaseAuth.getInstance().currentUser?.email.toString()
        )

        val comments = _post.comments
        comments.add(comment)

        FirebaseFirestore.getInstance()
            .collection("post")
            .document(id)
            .update("comments", comments)

        if (_post.owner != _myData.username) NotifyControl.addNotify(_user.username, content, _post.id, "comment")
    }
}