package com.example.cookingsocialnetwork.setting.changeProfile

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SettingChangeProfileViewModel: ViewModel() {
    lateinit var name: String
    lateinit var description: String
    lateinit var gender: String
    lateinit var birthday: Date

    init {
        getData()
    }

    private fun getData()
    {
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor")
            .get()
            .addOnSuccessListener {
                name = it.get("name").toString()
                description = it.get("description").toString()
                gender = it.get("gender").toString()
                name = it.get("name").toString()
            }
    }

    val nameTextWatcher: TextWatcher
        get()=object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                name = s.toString()
            }

        }

    val descriptionTextWatcher: TextWatcher
        get()=object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                description = s.toString()
            }

        }

    fun updateName()
    {
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor")
            .update("name", name)
    }

    fun updateDescription()
    {
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor")
            .update("description", description)
    }

    fun updateGender()
    {
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor")
            .update("gender", gender)
    }
}
