package com.example.cookingsocialnetwork.viewpost

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
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

    //kiểm tra xem người dùng đã thích bài viết đó chưa
    fun checkFavourite(): MutableLiveData<Boolean>
    {
        var check = false
        val index = post.value?.favourites?.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString())
        if (index!! > -1) check = true
        return MutableLiveData(check)
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
                    if (!check) favouritesUser.add(post.value!!.id)
                    else favouritesUser.remove(post.value!!.id)
                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(FirebaseAuth.getInstance().currentUser?.email.toString())
                        .update("favourites", favouritesUser)
                }
            }
    }
}