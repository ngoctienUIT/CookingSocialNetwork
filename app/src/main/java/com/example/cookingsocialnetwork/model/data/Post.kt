package com.example.cookingsocialnetwork.model.data

import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDateTime

data class Post(
    @Transient
    var id: String, //id bài viết
    var level: Int, // độ khó
    var cookingTime: String, // thời gian nấu
    var description: MutableList<String>, // mô tả
    var favourites: MutableList<String>, // danh sách username đã thích bài viết
    var images: MutableList<String>, // danh sách ảnh
   // var comments: MutableList<Map<String, Any>>, // danh sách commment
    var ingredients: MutableList<Map<String, Any>>, // danh sách nguyên liệu
    var methods: MutableList<Map<String, String>>, //danh sách các bước làm món ăn
    var servers: String, // số người ăn
    var nameFood: String, // tên món ăn
    var owner: String, // username chủ bài viết
   // var rate: Map<String, MutableList<String>>, // các lượt đánh giá
   // var share: Int // số lượt share
    var timePost : LocalDateTime

) {
//    constructor() : this(
//        "", 0, "", mutableListOf(), mutableListOf(),
//        mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), "", "", "", mapOf(), 0
//    )
    constructor() : this(
        "", 0, "", mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf(), mutableListOf(), "", "", "", LocalDateTime.now()
    )

    fun getData(document: DocumentSnapshot) {
        if (document.id != "count") {
            id = document.data?.get("id") as String
            level = document.data?.get("nameFood") as Int
            cookingTime = document.data?.get("cookingTime") as String
            description = document.data?.get("description") as MutableList<String>
            favourites = document.data?.get("favourites") as MutableList<String>
            images = document.data?.get("images") as MutableList<String>
          //  comments = document.data?.get("comments") as MutableList<Map<String, Any>>
            ingredients = document.data?.get("ingredients") as MutableList<Map<String, Any>>
            methods = document.data?.get("methods") as MutableList<Map<String, String>>
            servers = document.data?.get("nameFood") as String
            nameFood = document.data?.get("nameFood") as String
            owner = document.data?.get("owner") as String
            //rate = document.data?.get("rate") as Map<String, MutableList<String>>
           // share = document.data?.get("share") as Int
        }
    }
    fun setData(document: DocumentSnapshot){
        id = document.data?.get("id") as String
        level = document.data?.get("nameFood") as Int
        cookingTime = document.data?.get("cookingTime") as String
        description = document.data?.get("description") as MutableList<String>
        favourites = document.data?.get("favourites") as MutableList<String>
        images = document.data?.get("images") as MutableList<String>
        ingredients = document.data?.get("ingredients") as MutableList<Map<String, Any>>
        methods = document.data?.get("methods") as MutableList<Map<String, String>>
        servers = document.data?.get("nameFood") as String
        nameFood = document.data?.get("nameFood") as String
        owner = document.data?.get("owner") as String
        timePost = document.data?.get("timePost") as LocalDateTime
    }
}