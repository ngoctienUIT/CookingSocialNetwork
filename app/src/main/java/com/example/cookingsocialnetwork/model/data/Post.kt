package com.example.cookingsocialnetwork.model.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.time.LocalDateTime

data class Post(
    @Transient
    var cookingTime: String, // thời gian nấu
    var description: String, // mô tả
    var favourites: MutableList<String>, // danh sách username đã thích bài viết
    var id: String, //id bài viết
    var images: MutableList<String>, // danh sách ảnh
    var ingredients: MutableList<String>, // danh sách nguyên liệu
    var level: String, // độ khó
    var methods: MutableList<String>,//danh sách các bước làm món ăn
    var nameFood: String, // tên món ăn
    var owner: String, // username chủ bài viết
    var servers: String, // số người ăn
    var timePost : HashMap<String, Any>,
    var comments: MutableList<Map<String, Any>>, // danh sách commment
    var share: Long,
    var avatarOwner:String,
    var infoOwner:Map<String, Any>,
) {

    constructor() : this(
        "",   "", mutableListOf(), "", mutableListOf(),
        mutableListOf(),"0", mutableListOf(), "", "", "",
        hashMapOf(), mutableListOf(), 0, "", mapOf()
    )

    fun  getData(document: DocumentSnapshot) {
        try {
            comments = document.data?.get("comments") as MutableList<Map<String, Any>>
            cookingTime = document.data?.get("cookingTime") as String
            description = document.data?.get("description") as String
            favourites = document.data?.get("favourites") as MutableList<String>
            id = document.data?.get("id") as String
            images = document.data?.get("images") as MutableList<String>
            ingredients = document.data?.get("ingredients") as MutableList<String>
            level =  document.data?.get("level") as String
            methods = document.data?.get("methods") as MutableList<String>
            nameFood = document.data?.get("nameFood") as String
            owner = document.data?.get("owner") as String
            servers = document.data?.get("nameFood") as String
            share = document.data?.get("share") as Long
            timePost = document.data?.get("timePost") as HashMap<String, Any>
            infoOwner = document.data?.get("infoOwner") as Map<String, Any>
            owner = infoOwner["name"].toString()
            avatarOwner = infoOwner["avatar"].toString()

        }catch (e : Exception){
            throw e
        }

    }

    suspend fun  takeDataToHome(document: DocumentSnapshot) = coroutineScope {
        try {
            val job = async {
                owner = document.data?.get("owner") as String
                var info:Map<String, Any> = mapOf();
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(owner)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            return@addSnapshotListener
                        }
                        if (snapshot != null && snapshot.exists()) {
                            info = snapshot.data?.get("info") as Map<String, Any>
                            comments = document.data?.get("comments") as MutableList<Map<String, Any>>
                            cookingTime = document.data?.get("cookingTime") as String
                            description = document.data?.get("description") as String
                            favourites = document.data?.get("favourites") as MutableList<String>
                            id = document.data?.get("id") as String
                            images = document.data?.get("images") as MutableList<String>
                            ingredients = document.data?.get("ingredients") as MutableList<String>
                            level =  document.data?.get("level") as String
                            methods = document.data?.get("methods") as MutableList<String>
                            nameFood = document.data?.get("nameFood") as String
                            servers = document.data?.get("nameFood") as String
                            share = document.data?.get("share") as Long
                            timePost = document.data?.get("timePost") as HashMap<String, Any>
                            Log.i("getInfo", info.size.toString())
                        }
                    }
            }
            job.await()
        }catch (e : Exception){
            throw e
        }

    }

}
