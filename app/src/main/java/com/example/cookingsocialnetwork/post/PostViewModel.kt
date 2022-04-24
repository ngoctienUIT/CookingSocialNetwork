package com.example.cookingsocialnetwork.post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PostViewModel() :ViewModel() {

    var mListUriLiveData = MutableLiveData<MutableList<Uri>>()

    init {
        mListUriLiveData.value  = mutableListOf()
    }

    fun addUriIntoListUris(uri: Uri ) {
        mListUriLiveData.value?.add(uri)
        mListUriLiveData.value = mListUriLiveData.value
    }
    fun removeUriListUris(uri: Uri){
       if(mListUriLiveData.value?.contains(uri) == true){
           mListUriLiveData.value?.remove(uri)
       }
    }


}