package com.example.cookingsocialnetwork.post

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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

class PostPage : AppCompatActivity() {
    private var imageUri: Uri? = null                // imageUrl

    private lateinit var viewModel: PostViewModel
    private lateinit var databinding: ActivityPostPageBinding

    private val SELECT_PICTURE = 200
    private lateinit var uriSelectedImageFood:Uri


    private var arrEditTextIngredient:MutableList<String> = mutableListOf() // mảng lưu Text của thành phần món ăn
    private var arrEditTextMethod:MutableList<String> = mutableListOf() // mảng lưu Text của phương thức nấu


    private fun getIngredientText(){
        for (item in ingredient.children) {   // với mỗi LinearLayout con của LinearLayout ingredient
            var x : LinearLayout = item as LinearLayout;
            var etx:EditText = x[0] as EditText // với mỗi editText nằm ở vị trí 0 của LinearLayout con
            arrEditTextIngredient.add(etx.text.toString())
            // Log.d("Check getIngredientText", "The text of getIngredientText is: $arrEditTextIngredient")
        }
    }
    private fun getMethodText(){ // tương tự getIngredientText
        Toast.makeText(this, ingredient.childCount.toString(),Toast.LENGTH_LONG).show()
        for (item in ingredient.children) {
            var x : LinearLayout = item as LinearLayout;
            var etx:EditText = x[0] as EditText
            arrEditTextMethod.add(etx.text.toString())
            // Log.d("Check getIngredientText", "The text of getIngredientText is: $arrEditTextMethod")
        }
    }

    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }


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
                   databinding.foodImage.setImageURI(selectedImageUri)

                }
            }
        }
    }

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
            upLoadImageToFirebase(uriSelectedImageFood!!)
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

    private fun initPost(urlImageUpdated : String){
        getIngredientText()
        getMethodText()
        Log.d("PostPagegg", "Succesfully upadated image with: $urlImageUpdated")
        val newPostData = FirebaseFirestore.getInstance().collection("post").document();
        val postData = hashMapOf(
            "id" to "${newPostData.id}",
            "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "images" to urlImageUpdated,
            "nameFood" to databinding.nameFood.text.toString(),
            "description" to arrEditTextIngredient,
            "cookingTime" to databinding.cookingTime.text.toString(),
            "servers" to databinding.txtServes.text.toString(),
            "Level" to "Chua biet",
            "methods" to arrEditTextMethod,
            "favourites" to "0",
        )
        newPostData.set(postData);
    }
    private fun upLoadImageToFirebase(uri: Uri)  {

        val storageRef = FirebaseStorage.getInstance().reference
        val ref = storageRef.child("post/${uri.lastPathSegment}")
        var uploadTask = ref.putFile(uri!!)
//            .addOnSuccessListener {
//                Log.d("cc", "dowload successfull with: ${it.metadata?.path}")
//            }
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

                Log.d("PostPage", "Succesfully upadated image with: ${task.result}")
                // push profile bài post lên firebase, cái này sẽ luôn được thực hiện sau cùng
                // dù có để initpost() lên trước nên phải initpost ở đây luôn :v
                initPost(task.result.toString())
            } else {
                // Handle failures
                // ...
            }
        }

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