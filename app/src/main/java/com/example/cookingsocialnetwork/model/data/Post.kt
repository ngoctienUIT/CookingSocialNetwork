package com.example.cookingsocialnetwork.model.data

import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception

data class Post(
    @Transient
    var comments: MutableList<Map<String, Any>>, // danh sách commment
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
    var share: Long,
    var timePost: Time,

    ) {

    constructor() : this(
        mutableListOf(),"",   "", mutableListOf(), "", mutableListOf(),
        mutableListOf(),"0", mutableListOf(), "", "", "",
        0, Time(),
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
            val time = document.data?.get("timePost") as HashMap<String, Any>
            timePost.getTime(time)

        }catch (e : Exception){
            throw e
        }
    }
}
