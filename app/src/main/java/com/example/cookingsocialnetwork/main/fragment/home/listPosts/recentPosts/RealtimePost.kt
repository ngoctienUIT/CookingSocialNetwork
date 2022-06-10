package com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts

import com.example.cookingsocialnetwork.model.data.Post
import io.reactivex.Observable
import java.time.LocalDateTime


data class RealtimePost (
    val id: String,
    val post: Observable<Post>
) 