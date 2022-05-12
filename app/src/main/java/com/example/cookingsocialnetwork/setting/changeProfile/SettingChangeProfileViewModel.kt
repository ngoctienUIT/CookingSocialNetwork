package com.example.cookingsocialnetwork.setting.changeProfile

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SettingChangeProfileViewModel: ViewModel() {
    private var _user: User = User()
    var user: MutableLiveData<User> = MutableLiveData()

    init {
        user.value = _user
        getData()
    }

    private fun getData() {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                _user.getData(it)
                user.value = _user
            }
    }

    val nameTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                _user.name = s.toString()
                user.value = _user
            }

        }

    val descriptionTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                _user.description = s.toString()
                user.value = _user
            }

        }

    fun updateInfo(uri: Uri?) {
        val storage: StorageReference = FirebaseStorage.getInstance().reference
        if (uri != null)
            storage.child("avatar/${FirebaseAuth.getInstance().currentUser?.email}.png")
                .putFile(uri)
                .addOnSuccessListener {
                    storage.child("avatar/${FirebaseAuth.getInstance().currentUser?.email}.png")
                        .downloadUrl.addOnSuccessListener {
                            uploadInfo(it)
                        }
                }
        else uploadInfo(null)
    }

    private fun uploadInfo(uri: Uri?) {
        val info = hashMapOf(
            "name" to user.value!!.name,
            "avatar" to user.value!!.avatar,
            "gender" to user.value!!.gender,
            "username" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "description" to user.value!!.description,
            "birthday" to user.value!!.birthday
        )

        if (uri != null) info["avatar"] = uri.toString()
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .update("info", info)
    }
}
