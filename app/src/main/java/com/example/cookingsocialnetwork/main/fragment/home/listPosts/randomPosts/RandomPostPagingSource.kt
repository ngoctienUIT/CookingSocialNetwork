package com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts

import android.os.Parcel
import android.os.Parcelable
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class RandomPostPagingSource(private val db: FirebaseFirestore) : PagingSource<QuerySnapshot, Post>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {
        return try {
            val currentPage = params.key ?: db.collection("post")
                .limit(10)
                .whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .get()
                .await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            val nextPage = db.collection("post")
                .limit(10)
                .whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(

                data = currentPage.toObjects(Post::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Post>): QuerySnapshot? {
        TODO("Not yet implemented")
    }

}