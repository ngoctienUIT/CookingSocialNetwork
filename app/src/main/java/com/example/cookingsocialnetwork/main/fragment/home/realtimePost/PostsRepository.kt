package com.example.cookingsocialnetwork.main.fragment.home.realtimePost

import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.Observable
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class PostsRepository @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getPosts(
        pageSize: Int,
        loadBefore: String? = null,
        loadAfter: String? = null
    ): List<RealtimePost> {
        var query = db.collection("post").limit(pageSize.toLong())
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

        return query.get()
            .await()
            .map {
                RealtimePost(
                    it.id,
                    getPost(it.id)
                )
            }


    }

    private fun getPost(itemId: String): Observable<Post> {

        return  Observable.create<Post> {
            emitter ->
              db.collection("post")
                  .document(itemId)
                  .addSnapshotListener { snapshot, exception ->
                      if (exception != null) {
                          emitter.onError(exception)
                      } else if (snapshot != null && snapshot.exists()) {
                  //        android.util.Log.i("onNext", "ahihi")
                          val post = Post();
                              post.getData(snapshot)
                              emitter.onNext(post)

//                          emitter.onNext(
//                              snapshot.toObject (Post::class.java)
//                                  ?: throw IllegalArgumentException()
//                          )

                      } else {
                          emitter.onError(Throwable("Post does not exist"))

                      }

                  }
        }
    }


}

