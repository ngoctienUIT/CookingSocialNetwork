package com.example.cookingsocialnetwork.model

import com.google.firebase.firestore.DocumentSnapshot

data class Post(
    var id: String, //id bài viết
    var level: String, // độ khó
    var cookingTime: String, // thời gian nấu
    var description: MutableList<String>, // mô tả
    var favourites: MutableList<String>, // danh sách username đã thích bài viết
    var images: MutableList<String>, // danh sách ảnh
    var comments: MutableList<Map<String, Any>>, // danh sách commment
    var ingredients: MutableList<Map<String, Any>>, // danh sách nguyên liệu
    var servers: String, // số người ăn
    var nameFood: String, // tên món ăn
    var owner: String, // username chủ bài viết
    var rate: Map<String, MutableList<String>>, // các lượt đánh giá
    var share: Int // số lượt share
) {
    constructor() : this(
        "", "", "", mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf(), mutableListOf(), "", "", "", mapOf(), 0
    ) {

    }

    fun getData(document: DocumentSnapshot) {
        if (document.id.toString() != "count") {
            description = document.data?.get("description") as MutableList<String>
            cookingTime = document.data?.get("cookingTime") as String
            nameFood = document.data?.get("nameFood") as String
            owner = document.data?.get("owner") as String
            share = document.data?.get("share") as Int
            favourites = document.data?.get("favourites") as MutableList<String>
            images = document.data?.get("images") as MutableList<String>
            rate = document.data?.get("rate") as Map<String, MutableList<String>>
            ingredients = document.data?.get("ingredients") as MutableList<Map<String, Any>>
            comments = document.data?.get("comments") as MutableList<Map<String, Any>>
        }
    }
}