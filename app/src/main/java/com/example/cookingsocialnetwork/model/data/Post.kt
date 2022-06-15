package com.example.cookingsocialnetwork.model.data


import com.example.cookingsocialnetwork.post2.model.Ingredient
import com.example.cookingsocialnetwork.post2.model.StepFireBase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception

data class Post(
    @Transient
    var comments: MutableList<Map<String, Any>>, // danh sách commment
    var cookingTime: String, // thời gian nấu
    var description: String, // mô tả
    var favouritesCount: Long,
    var favourites: MutableList<String>, // danh sách username đã thích bài viết
    var id: String, //id bài viết
    var images: MutableList<String>, // danh sách ảnh
    var ingredients: MutableList<Ingredient>, // danh sách nguyên liệu
    var level: String, // độ khó
    var methods: MutableList<StepFireBase>,//danh sách các bước làm món ăn
    var nameFood: String, // tên món ăn
    var owner: String, // username chủ bài viết
    var servers: String, // số người ăn
    var share: Long,
    var timePost: Time,
    var timestamp : Timestamp
) {
    constructor() : this(
        mutableListOf<Map<String, Any>>(),"",   "",0, mutableListOf(), "", mutableListOf(),
        mutableListOf(),"0", mutableListOf(), "", "", "",
        0, Time(), Timestamp.now()
    )

    fun  getData(document: DocumentSnapshot) {
        try {
            comments = document.data?.get("comments") as MutableList<Map<String, Any>>
            cookingTime = document.data?.get("cookingTime") as String
            description = document.data?.get("description") as String
            favourites = document.data?.get("favourites") as MutableList<String>
            favouritesCount = favourites.size.toLong()
            id = document.data?.get("id") as String
            images = document.data?.get("images") as MutableList<String>

            //get arrIngredient
            val arrIngredient = document.data?.get("ingredients") as ArrayList<HashMap<String, Any>>
            convertToIngredientList(arrIngredient)

            level =  document.data?.get("level") as String

            //get methods
            val arrMethods = document.data?.get("methods") as ArrayList<HashMap<String, Any>>
            convertToMethodList(arrMethods)


            nameFood = document.data?.get("nameFood") as String
            owner = document.data?.get("owner") as String
            servers = document.data?.get("servers") as String
            share = document.data?.get("share") as Long
            val time = document.data?.get("timePost") as HashMap<String, Any>
            timePost.getTime(time)
            timestamp = document.data?.get("timestamp") as Timestamp

        }catch (e : Exception){
            throw e
        }
    }

    private fun convertToIngredientList(arrIngredientArrFB :  ArrayList<HashMap<String, Any>>){
        if (ingredients.size == 0){
            for( i in 0 until  arrIngredientArrFB.count()){
                val x = arrIngredientArrFB[i]
                val ingredient = Ingredient(x["amount"].toString(), x["unit"].toString(),x["name"].toString())
                ingredients.add(ingredient)
                // Log.d("ingredient",  "Ingredient(" + ingredient.name + ", " + ingredient.amount + ", " + ingredient.unit + ")" )
            }
        }
    }

    private fun convertToMethodList(arrMethodFB :  ArrayList<HashMap<String, Any>>){
        if(methods.size  ==  0){
            for( i in 0 until  arrMethodFB.count()){
                val x = arrMethodFB[i]
                val step = StepFireBase(x["image"].toString(), x["step"].toString())
                methods.add(step)
                // Log.d("StepFireBase", step.image + " " + step.step )
            }
        }
    }
}
