package com.example.cookingsocialnetwork.main.fragment.home.realtimePost

import com.example.cookingsocialnetwork.model.data.Post
import io.reactivex.Observable
import java.io.Serializable


data class RealtimePost (
    val id: String,
    val post: Observable<Post>
) 