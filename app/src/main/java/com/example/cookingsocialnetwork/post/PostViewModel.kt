package com.example.cookingsocialnetwork.post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PostViewModel() :ViewModel() {

    var  mListUri : MutableList<Uri> = mutableListOf()


    fun addUriIntoListUris(uri: Uri ) {
        mListUri.add(uri)
    }
    fun removeUriListUris(uri: Uri){
       if(mListUri.contains(uri)){
           mListUri.remove(uri)
       }
    }
    fun takeListUris(): MutableList<Uri>? {
        return mListUri

    }


}