package com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.QuerySnapshot as QuerySnapshot

class RandomPostPagingSource(private val db: FirebaseFirestore) : PagingSource<QuerySnapshot, Post>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {
        return try {
            val currentPage = params.key ?: db.collection("post")
                .limit(10)
                //.whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .get()
                .await()

            val randomCurrentPage = currentPage.shuffled()


            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            val nextPage = db.collection("post")
                .limit(10)
                .whereNotEqualTo("owner", FirebaseAuth.getInstance().currentUser?.email)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()
             val randomSnapShotNextPage =  nextPage.shuffled()

            LoadResult.Page(


                //data = currentPage.toObjects(Post::class.java),
                data = takeListValue(randomCurrentPage),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    private fun takeListValue(listQueryDocumentSnapshot: List<QueryDocumentSnapshot>) : List<Post>{
        val listValue = mutableListOf<Post>()
        for (snap in listQueryDocumentSnapshot){
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