package com.example.cookingsocialnetwork.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityPostPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_page.*
import kotlin.math.E

class PostPage : AppCompatActivity() {

    private lateinit var viewModel: PostViewModel
    private lateinit var databinding: ActivityPostPageBinding

    private var arrEditTextIngredient:MutableList<String> = mutableListOf() // mảng lưu Text của thành phần món ăn
    private var arrEditTextMethod:MutableList<String> = mutableListOf() // mảng lưu Text của phương thức nấu


    private var listImageUri: MutableList<Uri> = mutableListOf()            // imageUrl

    private var imagesChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data?.clipData !=null) {
                //chọn nhiều ảnh
                val count: Int = result.data!!.clipData!!.itemCount
                for (i in 0 until count)
                {
                    val imageUri = result.data!!.clipData!!.getItemAt(i).uri
                    listImageUri.add(imageUri)
                }
            }
            else
            {
                //chọn 1 ảnh
                val imageUri = result.data?.data
                listImageUri.add(imageUri!!)
                databinding.foodImage.setImageURI(imageUri)
            }
        }
    }

    private fun getIngredientText(){
        for (item in ingredient.children) {   // với mỗi LinearLayout con của LinearLayout ingredient
            var x : LinearLayout = item as LinearLayout;
            var etx:EditText = x[0] as EditText // với mỗi editText nằm ở vị trí 0 của LinearLayout con
            arrEditTextIngredient.add(etx.text.toString())
            // Log.d("Check getIngredientText", "The text of getIngredientText is: $arrEditTextIngredient")
        }
    }
    private fun getMethodText(){ // tương tự getIngredientText

        for (item in ingredient.children) {
            var x : LinearLayout = item as LinearLayout;
            var etx:EditText = x[0] as EditText
            arrEditTextMethod.add(etx.text.toString())
            // Log.d("Check getIngredientText", "The text of getIngredientText is: $arrEditTextMethod")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

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

        databinding.post.setOnClickListener(){
            upLoadImageToFirebase()
            finish()
        }

        databinding.backPost.setOnClickListener()
        {
            finish()
        }

        add_ingredient.setOnClickListener {
            ingredient.addView(drawUserTextInput())
        }
      //  add_ingredient.setOnClickListener()
        add_making_method.setOnClickListener {
            method.addView(drawUserTextInput())
        }

    }

    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)

        // pass the constant to compare it
        // with the returned requestCode
        imagesChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE) // không còn được sử dụng
    }

    private fun initPost(listUri: MutableList<String>){
        getIngredientText()
        getMethodText()

        val newPostData = FirebaseFirestore.getInstance().collection("post").document();
        val postData = hashMapOf(
            "id" to newPostData.id,
            "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "images" to listUri,
            "nameFood" to databinding.nameFood.text.toString(),
            "description" to arrEditTextIngredient,
            "cookingTime" to databinding.cookingTime.text.toString(),
            "servers" to databinding.txtServes.text.toString(),
            "Level" to "Chua biet",
            "methods" to arrEditTextMethod,
            "favourites" to mutableListOf<String>(),
        )
        newPostData.set(postData);
    }
    private fun upLoadImageToFirebase() {

        val listUri: MutableList<String> = mutableListOf()

        val storageRef = FirebaseStorage.getInstance().reference

        for (i in 0 until listImageUri.count()) {

            val ref = storageRef.child("post/${listImageUri[i].lastPathSegment}")
            val uploadTask = ref.putFile(listImageUri[i])

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
                   // uploadTask.pause()
                    listUri.add(task.result.toString())
                   // Log.d("PostPage", "Succesfully upadated an image with: ${task.result}")

                    //nếu đã get đủ url ảnh
                    if(listUri.count() == listImageUri.count()){
                        initPost(listUri)
                        Log.d("PostPage", "Succesfully upadated an image with: $listUri")
                    }
                   // uploadTask.resume()

                } else {
                    // Handle failures
                    // ...
                }
            }

        }
    }

    fun deleteIngredient(view: View){
        // edit text
        val newText = EditText(this)
        newText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1F
        )
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