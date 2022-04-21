package com.example.cookingsocialnetwork.post.chooseImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FragmentClickedImageChoosedViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FragmentClickedImageChoosedViewModel() as T
    }
}