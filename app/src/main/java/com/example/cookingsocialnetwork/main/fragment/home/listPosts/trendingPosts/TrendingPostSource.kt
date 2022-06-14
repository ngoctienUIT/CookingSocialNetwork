package com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.fetch.SourceResult
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class TrendingPostSource(private val db: FirebaseFirestore) : PagingSource<QuerySnapshot, Post>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {
        return try {
            val currentPage = params.key ?: db.collection("post")
                .limit(5)
               // .whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                //.orderBy("favourites")
                .get()
                .await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

           /* val nextPage = db.collection("post")
                .limit(5)
                .whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()*/

            LoadResult.Page(

                //data = currentPage.toObjects(Post::class.java),
                data = takeListValue(currentPage),
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    private fun takeListValue(querySnapshot: QuerySnapshot) : List<Post>{
        val listValue = mutableListOf<Post>()

        for (snap in querySnapshot){
            val post = Post();
            post.getData(snap);
            listValue.add(post)
        }
        return listValue
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Post>): QuerySnapshot? {
        TODO("Not yet implemented")
    }

}