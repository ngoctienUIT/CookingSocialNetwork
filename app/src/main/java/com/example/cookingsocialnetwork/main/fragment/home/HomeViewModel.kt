package com.example.cookingsocialnetwork.main.fragment.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts.RandomPostPagingSource
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.RecentPostsPagingSource
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts.TrendingPostSource
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts.TrendingSlide
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class HomeViewModel: ViewModel() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var posts:MutableList<Post> = mutableListOf()
    private var _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()
    private var post: MutableLiveData<Post> = MutableLiveData()
    private var listTrendingSlide: MutableList<TrendingSlide> = mutableListOf()
    private var mutableLiveDataTrendingSlide : MutableLiveData<MutableList<TrendingSlide>> = MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
//        listenToDataPost()
        takeTrendingPosts()
    }

    /*private val viewModelJob =  SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(10)
        .setPageSize(20)
        .build()

    // data recent posts
   // val x = postRecentRepository.
    val listPosts: LiveData<PagedList<RealtimePost>> =
        LivePagedListBuilder<String, RealtimePost>(
            PostRecentDataSource.Factory(postRecentRepository, uiScope),
            config
        ).build()*/
    //data recen posts
    val flowRecentPosts = Pager(PagingConfig(20)) {
        RecentPostsPagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)

    //data random posts
    val flowRandomPosts = Pager(PagingConfig(20)) {

        RandomPostPagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)

    //data trending post
    val flowTrendingPosts = Pager(PagingConfig(5)){

        TrendingPostSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)



   /* override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/


    var getPosts: MutableLiveData<MutableList<Post>>
        get() {
            return _posts
        }
        set(value) {
            _posts = value
        }

    private fun takeTrendingPosts(){

            firestore.collection("post")
                .addSnapshotListener()
                { snapshot, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val documents = snapshot.documents
                            documents.forEach()
                            { documentSnapshot ->
                                documentSnapshot.data!!.let {
                                    val checkData =  (it["images"] as MutableList<*>)
                                    if(checkData.size > 0){
                                        Log.d("hoicham", documentSnapshot.id )
                                        listTrendingSlide.add(
                                            TrendingSlide(
                                                documentSnapshot.id,
                                                (it["images"] as MutableList<*>)[0]!! as String,
                                                it["nameFood"]!! as String,
                                                (it["favourites"]!!  as MutableList<*>).size.toString()
                                            )
                                        )
                                    }
                                    else{
                                        listTrendingSlide.add(
                                            TrendingSlide(
                                                documentSnapshot.id,
                                                R.drawable.food_picker.toString(),
                                                it["nameFood"]!! as String,
                                                (it["favourites"]!!  as MutableList<*>).size.toString()
                                            )
                                        )
                                    }

                                    }
                                }

                        mutableLiveDataTrendingSlide.value = listTrendingSlide
                    }
                }
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

    //l???y d??? li???u c???a b??i vi???t hi???n t???i
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

    //ki???m tra xem ng?????i d??ng ???? th??ch b??i vi???t ???? ch??a
    fun checkFavourite(): MutableLiveData<Boolean>
    {
        var check = false
        val index = post.value?.favourites?.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString())
        if (index!! > -1) check = true
        return MutableLiveData(check)
    }

    //update d??? li???u sau khi nh???n th??ch ho???c b??? th??ch
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