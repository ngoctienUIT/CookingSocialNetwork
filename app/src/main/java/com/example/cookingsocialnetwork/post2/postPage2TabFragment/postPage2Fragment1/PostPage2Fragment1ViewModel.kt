package com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1

import android.net.Uri
import androidx.lifecycle.ViewModel

class PostPage2Fragment1ViewModel :ViewModel() {
    var  mListUri : MutableList<Uri> = mutableListOf()


    fun addUriIntoListUris(uri: Uri) {
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