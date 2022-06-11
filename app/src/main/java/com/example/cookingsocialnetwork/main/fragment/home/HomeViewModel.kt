package com.example.cookingsocialnetwork.main.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts.RandomPostPagingSource
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.PostRecentDataSource
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.PostRecentRepository
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.RealtimePost
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts.TrendingPostSource
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class HomeViewModel @Inject constructor(postRecentRepository : PostRecentRepository): ViewModel() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var posts:MutableList<Post> = mutableListOf()
    private var _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()
    private var post: MutableLiveData<Post> = MutableLiveData()

    private val viewModelJob =  SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(10)
        .setPageSize(20)
        .build()

    // data recent posts
    val listPosts: LiveData<PagedList<RealtimePost>> =
        LivePagedListBuilder<String, RealtimePost>(
            PostRecentDataSource.Factory(postRecentRepository, uiScope),
            config
        ).build()

    //data random posts
    val flowRandomPosts = Pager(PagingConfig(20)) {

        RandomPostPagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)

    //data trending post
    val flowTrendingPosts = Pager(PagingConfig(5)){

        TrendingPostSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
//        listenToDataPost()
    }

    var getPosts: MutableLiveData<MutableList<Post>>
        get() {
            return _posts
        }
        set(value) {
            _posts = value
        }

    private fun listenToDataPost() {
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
                        val post = Post()
                        post.getData(it)
                        posts.add(post)
                    }
                    _posts.value = posts
                }
            }
    }

    //lấy dữ liệu của bài viết hiện tại
    fun getPost(id: String): MutableLiveData<Post>
    {
        var index:Int = -1
        for (i in 0..posts.count())
            if (posts.elementAt(i).id == id)
            {
                index = i
                break
            }
        if (index > -1)
            post.value = posts.elementAt(index)
        return post
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
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("favourites")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    favouritesUser = it.get("favourites") as MutableList<String>
                    if (!check) favouritesUser.add(post.value!!.id)
                    else favouritesUser.remove(post.value!!.id)
                    FirebaseFirestore.getInstance()
                        .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
                        .document("favourites")
                        .update("favourites", favouritesUser)
                }
            }
    }
}