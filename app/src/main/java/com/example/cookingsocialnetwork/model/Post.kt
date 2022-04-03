package com.example.cookingsocialnetwork.model

import android.net.Uri

data class Post (val imgaeUri : Uri,
                 val title : String,
                 val description : String,
                 val serves: String,
                 val cookTime : String,
                 val ingredient : List<String>,
                 val method : List<Method>){
    class Method(val imgaeUri: Uri, val step: String) {

    }


}

