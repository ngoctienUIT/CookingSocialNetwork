package com.example.cookingsocialnetwork.post



import androidx.lifecycle.ViewModel

import com.google.firebase.firestore.FirebaseFirestore


class PostViewModel(
    var id: String?,
    var img: String?,
    var nameFood: String?,
    var description: String?,
    var ration: Int = 0,
    var cookingTime: Float = 0.0F,
    var ingredients: MutableList<String>,
    var making: MutableList<String>) :ViewModel() {

    constructor(): this("ádfa",
                      "ádfasd",
                  "sdfasd",
                "ádasdf",
                    0,
                0F,
        mutableListOf(), mutableListOf()) {
    }

    fun initPostId(){
        val newPostData = FirebaseFirestore.getInstance().collection("post").document();
        val postData = hashMapOf(
            "id" to "${newPostData.id}",
            "img" to "chua co",
            "nameFood" to nameFood,
            "description" to description,
            "ration" to ration,
            "cookingTime" to cookingTime,
            "ingredients" to mutableListOf<String>(),
            "making" to mutableListOf<String>(),
        )
        newPostData.set(postData);
    }

    fun onClickPost() {
        initPostId();
    }



}