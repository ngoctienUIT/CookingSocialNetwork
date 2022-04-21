package com.example.cookingsocialnetwork.post




import android.content.ClipData.Item
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.databinding.ActivityPostPageBinding


class PostViewModel() :ViewModel() {

    var mListUriLiveData = MutableLiveData<MutableList<Uri>>()

    init {
        mListUriLiveData.value  = mutableListOf()
    }

    fun addUriIntoListUris(uri: Uri ) {
        mListUriLiveData.value?.add(uri)
        mListUriLiveData.value = mListUriLiveData.value
    }


}