package com.example.cookingsocialnetwork.main.fragment.home.realtimePost

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class PostsDataSource (
    private val postsRepository: PostsRepository,
    private val scope: CoroutineScope
    ) : ItemKeyedDataSource<String, RealtimePost>() {

        class Factory(
            private val myRepository: PostsRepository,
            private val scope: CoroutineScope
        ) : DataSource.Factory<String, RealtimePost>() {
            override fun create(): DataSource<String, RealtimePost> =
                PostsDataSource(myRepository, scope)
        }

        override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<RealtimePost>
        ) {
            scope.launch {
                val items = postsRepository.getPosts(params.requestedLoadSize)
                callback.onResult(items)
            }
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RealtimePost>) {
            scope.launch {
                val items = postsRepository.getPosts(params.requestedLoadSize, loadAfter = params.key)
                callback.onResult(items)
            }
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RealtimePost>) {
            scope.launch {
                val items = postsRepository.getPosts(params.requestedLoadSize, loadBefore = params.key)
                callback.onResult(items)
            }
        }

        override fun getKey(item: RealtimePost) = item.id
    }