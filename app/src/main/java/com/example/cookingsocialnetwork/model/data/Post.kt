package com.example.cookingsocialnetwork.model.data

import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDateTime

data class Post(
    @Transient
    var cookingTime: String, // thời gian nấu
    var description: String, // mô tả
    var favourites: MutableList<String>, // danh sách username đã thích bài viết
    var id: String, //id bài viết
    var images: MutableList<String>, // danh sách ảnh
    var ingredients: MutableList<String>,
    var level: String, // độ khó
    var methods: MutableList<String>,
    //var methods: MutableList<Map<String, String>>, //danh sách các bước làm món ăn
    var nameFood: String, // tên món ăn
    var owner: String, // username chủ bài viết
    var servers: String, // số người ăn
    var timePost : HashMap<String, Any>,

    var comments: MutableList<Map<String, Any>>, // danh sách commment
  //  var ingredients: MutableList<Map<String, Any>>, // danh sách nguyên liệu

   // var rate: Map<String, MutableList<String>>, // các lượt đánh giá
    var share: Long // số lượt share
) {
    //    constructor() : this(
//        "", 0, "", mutableListOf(), mutableListOf(),
//        mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), "", "", "", mapOf(), 0
//    )
    constructor() : this(
        "",   "", mutableListOf(), "", mutableListOf(),
        mutableListOf(),"", mutableListOf(), "", "", "", hashMapOf(), mutableListOf(), 0
    )

    fun getData(document: DocumentSnapshot) {
        if (document.id != "count") {
            cookingTime = document.data?.get("cookingTime") as String
            description = document.data?.get("description") as String
            favourites = document.data?.get("favourites") as MutableList<String>
            id = document.data?.get("id") as String
            images = document.data?.get("images") as MutableList<String>
            ingredients = document.data?.get("ingredients") as MutableList<String>
            level = document.data?.get("nameFood") as String
            methods = document.data?.get("methods") as MutableList<String>
            nameFood = document.data?.get("nameFood") as String
            owner = document.data?.get("owner") as String
            servers = document.data?.get("nameFood") as String
            timePost = document.data?.get("timePost") as HashMap<String, Any>
            share = document.data?.get("share") as Long
            comments = document.data?.get("methods") as MutableList<Map<String, Any>>
        }

            //  comments = document.data?.get("comments") as MutableList<Map<String, Any>>
          //  ingredients = document.data?.get("ingredients") as MutableList<Map<String, Any>>
          //  methods = document.data?.get("methods") as MutableList<Map<String, String>>
            //rate = document.data?.get("rate") as Map<String, MutableList<String>>
            // share = document.data?.get("share") as Int
    }
}
