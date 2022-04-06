package com.example.cookingsocialnetwork.post

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.LanguageManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityPostPageBinding
import com.example.cookingsocialnetwork.mainActivity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_page.*

class PostPage : AppCompatActivity() {
    private var imageUri: Uri? = null                // imageUrl

    private lateinit var viewModel: PostViewModel
    private lateinit var databinding: ActivityPostPageBinding

    private val SELECT_PICTURE = 200
    private lateinit var uriSelectedImageFood:Uri
    private lateinit var urlImageUpdate: Uri

    fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }

    private var IVPreviewImage: ImageView? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    uriSelectedImageFood = selectedImageUri
                    // update the preview image in the layout
                    IVPreviewImage = findViewById(R.id.food_image)
                    IVPreviewImage?.setImageURI(selectedImageUri)

                }
            }
        }
    }

//   pick picture
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == 100) {
//            imageUri = data?.data
//            food_image.setImageURI(imageUri)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate view and obtain an instance of the binding class
        databinding= DataBindingUtil.setContentView(this, R.layout.activity_post_page)
        Log.i("GameFragment", "Called ViewModelProvider.get")

        // Get the viewModel
        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        databinding.postPageViewModel = viewModel
        databinding.lifecycleOwner = this
        databinding.foodImage.setOnClickListener{
            imageChooser()
        }

        databinding.btnPost.setOnClickListener{
            // upLoadImageToFirebase(uriSelectedImageFood)
            initPost()
            finish()

        }
        //Này không biết sao nó không hay bằng cái kia
//        food_image.setOnClickListener {
//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, 100) // requestCode = 100
//        }
        add_ingredient.setOnClickListener {
            ingredient.addView(drawUserTextInput())
        }
        add_making_method.setOnClickListener {
            method.addView(drawUserTextInput())
        }

    }


    private fun initPost(){
        val newPostData = FirebaseFirestore.getInstance().collection("post").document();
        val postData = hashMapOf(
            "id" to "${newPostData.id}",
            "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "images" to "Chua co",
           "nameFood" to databinding.nameFood.text.toString(),
            "description" to databinding.txtDec.text.toString(),
            "ration" to databinding.txtMethod.text.toString(),
            "ingredients" to mutableListOf<String>(),
            "cookingTime" to databinding.cookingTime.text.toString(),
            "making" to mutableListOf<String>(),
            "share" to "0",
            "favourites" to "0",
        )
        newPostData.set(postData);
    }
    private fun upLoadImageToFirebase(uri: Uri) {

        val storageRef = FirebaseStorage.getInstance().reference
        val ref = storageRef.child("post/${uri.lastPathSegment}")
        var uploadTask = ref.putFile(uri)
// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

        //getUrl
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
            } else {
                // Handle failures
                // ...
            }
        }
        urlImageUpdate = urlTask.result;
    }

    fun deleteIngredient(view: View){
        ingredient.removeView(view.parent as View)
    }
    private fun drawUserTextInput() : LinearLayout {
        // layout
        val newLayout = LinearLayout(this)
        newLayout.orientation = LinearLayout.HORIZONTAL
        newLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // edit text
        val newText = EditText(this)
        newText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1F
        )
        newLayout.addView(newText)
        // button delete
        val newCloseButton = Button(this)
        newCloseButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1F
        )
        newLayout.addView(newCloseButton)
        newCloseButton.setOnClickListener {
            (newLayout.parent as LinearLayout).removeView(newLayout)
        }
        return newLayout
    }
}