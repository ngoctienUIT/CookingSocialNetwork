package com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts

import coil.fetch.SourceResult
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source

class TrendingPostSource(private val db: FirebaseFirestore)