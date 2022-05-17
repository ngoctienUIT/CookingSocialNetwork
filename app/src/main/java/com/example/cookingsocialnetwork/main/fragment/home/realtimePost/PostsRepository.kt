package com.example.cookingsocialnetwork.main.fragment.home.realtimePost

import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.Observable
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostsRepository @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getPosts(
        pageSize: Int,
        loadBefore: String? = null,
        loadAfter: String? = null
    ): List<RealtimePost> {
        var query = db.collection("posts").limit(pageSize.toLong())


        loadBefore?.let {

            val item = db.collection("posts").document(it)
                .get()
                .await()

            query = query.endBefore(item)
        }

        loadAfter?.let {
            val item = db.collection("posts").document(it)
                .get()
                .await()

            query = query.startAfter(item)
        }

//        android.util.Log.i("hehehe", "${
//            query.get()
//                .await()
//                .map {
//                    RealtimePost(
//                        it.id,
//                        getPost(it.id)
//                    )
//                }
//                .count()
//        }")
        return query.get()
            .await()
            .map {
                RealtimePost(
                    it.id,
                    getPost(it.id)
                )
            }


    }

    private fun getPost(itemId: String): Observable<Post> =

        Observable.create<Post> { emitter ->
            db.collection("posts")
                .document(itemId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        emitter.onError(exception)
                    } else if (snapshot != null && snapshot.exists()) {
                        emitter.onNext(
                            snapshot.toObject(Post::class.java)
                                ?: throw IllegalArgumentException()

                        )

                    } else {
                        emitter.onError(Throwable("Post does not exist"))

                    }

                }
        }
}