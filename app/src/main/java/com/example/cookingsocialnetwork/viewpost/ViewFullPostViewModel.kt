package com.example.cookingsocialnetwork.viewpost

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.firestore.FirebaseFirestore

class ViewFullPostViewModel: ViewModel() {
    private var _post: Post = Post()
    var post: MutableLiveData<Post> = MutableLiveData()
    private var _user: User = User()
    var user: MutableLiveData<User> = MutableLiveData()
    var id: String = ""

    init {
        post.value = _post
        user.value = _user
    }

    fun getData() {
        Log.w("id data Post", id)
        FirebaseFirestore.getInstance()
            .collection("post")
            .document(id)
            .get()
            .addOnSuccessListener {
                val data = it.data?.get("nameFood").toString()
                Log.w("data Post", data)
                _post.getData(it)
                post.value = _post
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(post.value!!.ownerEmail)
                    .get()
                    .addOnSuccessListener { userSnap ->
                        _user.getData(userSnap)
                        user.value = _user
                    }
            }
    }
}