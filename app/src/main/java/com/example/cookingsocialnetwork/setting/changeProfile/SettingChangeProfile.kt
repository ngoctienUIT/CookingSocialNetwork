package com.example.cookingsocialnetwork.setting.changeProfile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivitySettingChangeProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.yalantis.ucrop.UCrop
import java.io.File


class SettingChangeProfile : AppCompatActivity() {
    private lateinit var viewModel: SettingChangeProfileViewModel
    private lateinit var databinding: ActivitySettingChangeProfileBinding


    private var getContent = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        val inputUri = it
        val outputUri = File(filesDir,"croppedImage.jpg").toUri()
        val listUri = listOf<Uri>(inputUri, outputUri)
        cropImage.launch(listUri)
    }

    private val uCropContract = object: ActivityResultContract<List<Uri>, Uri>() {
        override fun createIntent(context: Context, input: List<Uri>?): Intent {
            val inputUri = input?.get(0)
            val outputUri = input?.get(1)

            val uCrop = UCrop.of(inputUri!!, outputUri!!)
                .withAspectRatio(5f, 5f)
                .withMaxResultSize(800, 800)
            Log.w("Có1", "Có mà")

            return uCrop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return intent?.let {
                UCrop.getOutput(
                    it
                )
            }
        }
    }

    private val cropImage = registerForActivityResult(uCropContract)
    {
        if (it!=null) {
            databinding.ivAvatar.setImageURI(it)
            uploadAvatar(it)
        }
    }

    private fun selectGallery()
    {
        getContent.launch("image/*")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_change_profile)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_change_profile)
        val factory = SettingChangeProfileViewModelFactory()

        viewModel = ViewModelProvider(this,factory).get(SettingChangeProfileViewModel::class.java)
        databinding.viewModel = viewModel
        databinding.lifecycleOwner = this
        databinding.avatarBtn.setOnClickListener()
        {
            selectGallery()
        }
    }

    private fun uploadAvatar(uri: Uri) {
        val storage: StorageReference = FirebaseStorage.getInstance().reference
        storage.child("avatar/${FirebaseAuth.getInstance().currentUser?.email}.png")
            .putFile(uri)
            .addOnSuccessListener {
                storage.child("avatar/${FirebaseAuth.getInstance().currentUser?.email}.png")
                    .downloadUrl.addOnSuccessListener {
                    FirebaseFirestore.getInstance()
                        .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
                        .document("infor")
                        .update(
                            "avatar", it.toString()
                        )
                }
            }
    }
}
