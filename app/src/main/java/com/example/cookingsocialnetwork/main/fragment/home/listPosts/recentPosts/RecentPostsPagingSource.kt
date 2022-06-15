package com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class RecentPostsPagingSource(private val db: FirebaseFirestore) : PagingSource<QuerySnapshot, Post>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {
        return try {
            val currentPage = params.key ?: db.collection("post")
                .limit(10)
                //.whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            val nextPage = db.collection("post")
                .limit(10)
                //.whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(

                //data = currentPage.toObjects(Post::class.java),
                data = takeListValue(currentPage),
                prevKey = null,
                nextKey = nextPage
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
