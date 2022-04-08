package com.example.cookingsocialnetwork.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityPostPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_page.*

class PostPage : AppCompatActivity() {
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

    private lateinit var viewModel: PostViewModel
    private lateinit var databinding: ActivityPostPageBinding

//    private val SELECT_PICTURE = 200
//    private lateinit var uriSelectedImageFood:Uri

    private lateinit var arrEditTextIngredient:MutableList<String>

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
            upLoadImageToFirebase()
            finish()
        }

        add_ingredient.setOnClickListener {
            ingredient.addView(drawUserTextInput())
            getIngredientText()
        }
        //  add_ingredient.setOnClickListener()
        add_making_method.setOnClickListener {
            method.addView(drawUserTextInput())
        }
    }

    private fun getIngredientText(){
            Toast.makeText(this, ingredient.childCount.toString(),Toast.LENGTH_LONG).show()
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

// không còn được sử dụng
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                val selectedImageUri: Uri? = data?.data
//                if (null != selectedImageUri) {
//                    uriSelectedImageFood = selectedImageUri
//                    // update the preview image in the layout
//                   databinding.foodImage.setImageURI(selectedImageUri)
//
//                }
//            }
//        }
//    }

    private fun initPost(id : String, listUri: MutableList<String>) {
//        Log.d("PostPagegg", "Succesfully upadated image with: $urlImageUpdated")
        val newPostData = FirebaseFirestore.getInstance()
            .collection("post")
            .document(id)

        val postData = hashMapOf(
            "id" to newPostData.id,
            "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "images" to listUri,
            "nameFood" to databinding.nameFood.text.toString(),
            "level" to "Chua biet",
            "description" to databinding.txtDec.text.toString(),
            "ration" to hashMapOf(
                "1" to mutableListOf<String>(),
                "2" to mutableListOf(),
                "3" to mutableListOf(),
                "4" to mutableListOf(),
                "5" to mutableListOf()
            ),
            "ingredients" to mutableListOf<String>(),
            "cookingTime" to databinding.cookingTime.text.toString(),
            "making" to mutableListOf<String>(),
            "favourites" to mutableListOf<String>(),
        )

        newPostData.set(postData)
    }

    private fun upLoadImageToFirebase() {
        var countPost = FirebaseFirestore.getInstance()
            .collection("post")
            .document("count")
            .get()
            .addOnSuccessListener()
            { it ->
                var data = it.data?.get("countPost")
                var count = data.toString().toInt()
                var id: String = ""
                for (i in 1..(10 - count.toString().length)) id += "0"
                id += count.toString() // tạo id mới

                var listUri: MutableList<String> = mutableListOf()

                val storageRef = FirebaseStorage.getInstance().reference
                for (i in 0 until listImageUri.count()) {
                    var link = "post/${id}/${i}.png"

                    storageRef
                        .child(link)
                        .putFile(listImageUri.elementAt(i))
                        .addOnSuccessListener()
                        {
                            storageRef.child(link)
                                .downloadUrl.addOnSuccessListener {
                                    listUri.add(it.toString())
                                }
                        }
                }

                Log.d("PostPage link", listUri.toString())

//                initPost(id, listUri)

                count++
                FirebaseFirestore.getInstance()
                    .collection("post")
                    .document("count")
                    .update("countPost", count)

/*                val uploadTask = ref.putFile(uri)
            .addOnSuccessListener {
                Log.d("cc", "dowload successfull with: ${it.metadata?.path}")
            }
 Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                }.addOnSuccessListener {
                taskSnapshot ->
                     taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                     ...
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
                }*/
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
            LinearLayout.LayoutParams.MATCH_PARENT,
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