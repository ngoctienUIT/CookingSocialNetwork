package com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts

import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.Observable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRecentRepository @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getPosts(
        pageSize: Int,
        loadBefore: String? = null,
        loadAfter: String? = null,
    ): List<RealtimePost> {

        var query = db.collection("post")
            .limit(pageSize.toLong())
            //.whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
            .orderBy("timestamp", Query.Direction.DESCENDING)


        loadBefore?.let {
            val item = db.collection("post").document(it)
                .get()
                .await()
            query = query.endBefore(item)
        }

        loadAfter?.let {
            val item = db.collection("post").document(it)
                .get()
                .await()

            query = query.startAfter(item)
        }


        val listRealtimePost : MutableList<RealtimePost> = mutableListOf()
        query.get()
            .await()
            .forEach { queryDocumentSnapshot ->
                db.collection("post")
                    .document(queryDocumentSnapshot.id)
                    .get().addOnSuccessListener {
                        if(it.data != null){
                            val post = Post()
                            post.getData(it)
                            listRealtimePost.add(
                                RealtimePost(
                                    queryDocumentSnapshot.id,
                                    getPost(queryDocumentSnapshot.id)
                                )
                            )
                        }
                    }

            }
       /* val listRealtimePost = query.get()
            .await()
            .map {
                RealtimePost(
                    it.id,
                    getPost(it.id)
                )
            }*/

        return listRealtimePost
    }


    private fun getPost(itemId: String): Observable<Post> {
        return Observable.create<Post> { emitter ->
            db.collection("post")
                .document(itemId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        emitter.onError(exception)
                    } else if (snapshot != null && snapshot.exists()) {
                        val post = Post();
                        post.getData(snapshot)
                        emitter.onNext(post)

                    } else {
                        emitter.onError(Throwable("Post does not exist"))
                    }
                }
        }
    }
}

