package com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class PostRecentDataSource (
    private val postRecentRepository: PostRecentRepository,
    private val scope: CoroutineScope
    ) : ItemKeyedDataSource<String, RealtimePost>() {

        class Factory(
            private val myRepository: PostRecentRepository,
            private val scope: CoroutineScope
        ) : DataSource.Factory<String, RealtimePost>() {
            override fun create(): DataSource<String, RealtimePost> =
                PostRecentDataSource(myRepository, scope)
        }

        override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<RealtimePost>
        ) {
            scope.launch {
                val items = postRecentRepository.getPosts(params.requestedLoadSize)
                callback.onResult(items)
            }
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RealtimePost>) {
            scope.launch {
                val items = postRecentRepository.getPosts(params.requestedLoadSize, loadAfter = params.key)
                callback.onResult(items)
            }
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RealtimePost>) {
            scope.launch {
                val items = postRecentRepository.getPosts(params.requestedLoadSize, loadBefore = params.key)
                callback.onResult(items)
            }
        }

        override fun getKey(item: RealtimePost) = item.id
    }