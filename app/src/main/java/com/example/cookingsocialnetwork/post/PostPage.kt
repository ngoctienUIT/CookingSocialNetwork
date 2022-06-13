package com.example.cookingsocialnetwork.post
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityPostPageBinding
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage.FragmentClickedImageChoosed
import com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage.RecyclerAdapterImageChoosed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostPage : AppCompatActivity() {



    private lateinit var viewModel: PostViewModel
    private lateinit var databinding: ActivityPostPageBinding

    @SuppressLint("SimpleDateFormat")
    private val formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


    private var arrEditTextIngredient:MutableList<String> = mutableListOf() // mảng lưu Text của thành phần món ăn
    private var arrEditTextMethod:MutableList<String> = mutableListOf() // mảng lưu Text của phương thức nấu

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapterImageChoosed: RecyclerAdapterImageChoosed
    private var listImageUri: MutableList<Uri> = mutableListOf()  // image choose from device
    private var listImageUrlFB : MutableList<String> = mutableListOf() // image url from firebase

    private var imagesChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            if (result.data?.clipData !=null) {
                //chọn nhiều ảnh
                val count: Int = result.data!!.clipData!!.itemCount
                for (i in 0 until count)
                {
                    val imageUri = result.data!!.clipData!!.getItemAt(i).uri

                    //listImageUri.add(imageUri)
                    viewModel.addUriIntoListUris(imageUri)
                }
                addListUri()
            }
            else
            {
                //chọn 1 ảnh
                val imageUri = result.data?.data
                //listImageUri.add(imageUri!!)
                viewModel.addUriIntoListUris(imageUri!!)
                addListUri()
//                databinding.foodImage.setImageURI(imageUri)
            }
        }
    }

    private fun getIngredientText(){
        for (item in databinding.ingredients.children) { // với mỗi layout kiểu relativeLayout trong ingredients
            val x : RelativeLayout = item as RelativeLayout
            val etx:EditText = x[0] as EditText // với mỗi editText nằm ở vị trí 0 của RelativeLayout con
             arrEditTextIngredient.add(etx.text.toString())
           //  Log.d("Check getIngredientText", "${etx.text}")

        }
    }
    private fun getMethodText(){ // tương tự getIngredientText

        for (item in databinding.ingredients.children) {
            val x : RelativeLayout = item as RelativeLayout
            val etx:EditText = x[0] as EditText
            arrEditTextMethod.add(etx.text.toString())
            // Log.d("Check getIngredientText", "The text of getIngredientText is: $arrEditTextMethod")
        }
    }

    @SuppressLint("InflateParams")
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

        databinding.post.setOnClickListener {
            upLoadDataToFirebase()
            finish()
        }

        databinding.backPost.setOnClickListener()
        {
            finish()
        }

        databinding.addIngredient.setOnClickListener {
            val newLayout = LayoutInflater.from(this).inflate(R.layout.postpage_ingredient_child, null)
            databinding.ingredients.addView(newLayout)
            newLayout.findViewById<Button>(R.id.btn_Ingredients).setOnClickListener {
                (newLayout.parent as LinearLayout).removeView(newLayout)
            }
        }
      //  add_ingredient.setOnClickListener()
        databinding.addMakingMethod.setOnClickListener {
            val newLayout = LayoutInflater.from(this).inflate(R.layout.postpage_method_child, null)
            databinding.method.addView(newLayout)
            newLayout.findViewById<Button>(R.id.btn_Methods).setOnClickListener {
                (newLayout.parent as LinearLayout).removeView(newLayout)
            }
        }
//        databinding.viewPost.setOnClickListener{
//            val viewPost = Intent(this, ViewPostPage::class.java)
//            startActivity(viewPost)
//        }

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

    private fun addListUri(){

        //set RecycleView
        gridLayoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        // gridLayoutManager.scrollToPosition(0)
        databinding.recyclerViewImage.layoutManager = gridLayoutManager

        adapterImageChoosed = RecyclerAdapterImageChoosed(viewModel.mListUri)
            {
               /* val editGridClickedImageFragment = FragmentClickedImageChoosed()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container_fragmentGridClickedItem, editGridClickedImageFragment)
                transaction.addToBackStack(null)
                transaction.commit()*/
            }
        databinding.recyclerViewImage.adapter = adapterImageChoosed
        databinding.recyclerViewImage.setHasFixedSize(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun noticeDataChangeToRecyclerAdapterImageChoosed(){
        adapterImageChoosed.notifyDataSetChanged()
    }

    private fun initPost(){
        Log.d("PostPage", "initPost")
        getIngredientText()
        getMethodText()

        val newPostData = FirebaseFirestore.getInstance().collection("post").document()
        val postData = hashMapOf(
            "id" to newPostData.id,
            "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "images" to listImageUrlFB,
            "nameFood" to databinding.nameFood.text.toString(),
            "description" to databinding.txtDec.text.toString()  ,
            "cookingTime" to databinding.cookingTime.text.toString(),
            "servers" to databinding.txtServes.text.toString(),
            "level" to databinding.ratingLevel.numStars.toString(),
            "ingredients" to arrEditTextIngredient,
            "methods" to arrEditTextMethod,
            "favourites" to mutableListOf<String>(),
            "timePost" to LocalDateTime.now(),
            "share" to 0,
            "comments" to mutableListOf<Map<String, Any>>()
        )
        newPostData.set(postData)
        addIDPostToUser(newPostData.id)
    }

    private fun addIDPostToUser(IDNewPost: String){
        val updater = FirebaseFirestore.getInstance()
        updater.collection("user")
            .document("${FirebaseAuth.getInstance().currentUser?.email}")
            .update("post", FieldValue.arrayUnion(IDNewPost))

        Log.d("PostPage", "add ID successful")

    }

    private fun upLoadDataToFirebase() {

        Log.d("PostPage", "Loading...")

      //  val listUri: MutableList<String> = mutableListOf()

        val storageRef = FirebaseStorage.getInstance().reference

        listImageUri = viewModel.takeListUris()!! // take list Uris from PostpageViewModel

        for (i in 0 until listImageUri.count()) {

            val ref = storageRef.child("post/${listImageUri[i].lastPathSegment}")
            val uploadTask = ref.putFile(listImageUri[i])

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
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
                  //  listUri.add(task.result.toString())
                    listImageUrlFB.add(task.result.toString());
                   // Log.d("PostPage", "Succesfully upadated an image with: ${task.result}")

                    //nếu đã get đủ url ảnh
                    if(listImageUrlFB.count() == listImageUri.count()){
                       // initPost(listImageUrlFB)
                        initPost()
                       // Log.d("PostPage", "Succesfully upadated an image with: $listUri")
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
        //(view.parent as LinearLayout).removeView(view)
//        val newText = EditText(this)
//        newText.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            1F
//        )
    }
}


