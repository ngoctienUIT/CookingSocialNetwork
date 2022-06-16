package com.example.cookingsocialnetwork.post2

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.post2.model.Ingredient
import com.example.cookingsocialnetwork.post2.model.Step
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime

class  PostPage2 : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var backBtn: ImageView
    private lateinit var postBtn: Button
    //fields
    private lateinit var listImageUri : List<Uri>;
    private  var nameFood :String = "";
    private  var portion:String = "";
    private  var difficult: String = "";
    private  var prepTime:String = "";
    private  var bakingTime: String = "";
    private  var restTime:String = ""
    private var ingredientList: List<Ingredient> = mutableListOf()
    private var listStepLocal: MutableList<Step> = mutableListOf()
    private var listStepFB: MutableList<Step> = mutableListOf()
    private var foodNote:String = ""
    private var  listImageUrlFoodFB : MutableList<String> = mutableListOf()




//    private lateinit var previewBtn :Button
//    private lateinit var nextBtn: Button
    private val postPageAdapter = PostPage2ViewPagerAdapter(supportFragmentManager, lifecycle)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_post_page_2)

        tabLayout = findViewById(R.id.post_page_tab)
        backBtn = findViewById(R.id.post_page_btn_back)
        viewPager  = findViewById(R.id.post_page_viewPager)
        postBtn = findViewById(R.id.post_page_btn_post)
        postBtn.visibility = View.INVISIBLE
//        previewBtn = findViewById(R.id.post_page_preview_btn)
//        nextBtn = findViewById(R.id.post_page_next_button)

        viewPager.adapter = postPageAdapter
        viewPager.registerOnPageChangeCallback(
            object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            backBtn.setImageResource(R.drawable.ic_round_close)
                            backBtn.setColorFilter(ContextCompat.getColor(applicationContext, R.color.red))
                        }
                        4 -> {
//                            nextBtn.visibility = View.GONE
//                            previewBtn.text = "Xem lại trước khi đăng"
                            postBtn.visibility = View.VISIBLE
                        }
                        else -> {
                            backBtn.setImageResource(R.drawable.ic_back_ios)
                            backBtn.setColorFilter(ContextCompat.getColor(applicationContext, R.color.green))
                        }
                    }
                    super.onPageSelected(position)

                }

            }

        )
        TabLayoutMediator(tabLayout, viewPager){
                tabLayoutt, position ->
        }.attach()


        postBtn.setOnClickListener {
            if(postPageAdapter.fragment1.foodName.text.toString() == "" || postPageAdapter.fragment1.imageList.size ==0){
                val tab: TabLayout.Tab? = tabLayout.getTabAt(0)
                tab?.select()
                return@setOnClickListener
            }
            if(!postPageAdapter.fragment3.check){
                val tab: TabLayout.Tab? = tabLayout.getTabAt(2)
                tab?.select()
                return@setOnClickListener
            }
            if(!postPageAdapter.fragment4.check){
                val tab: TabLayout.Tab? = tabLayout.getTabAt(3)
                tab?.select()
                return@setOnClickListener
            }

            post()
        }

        backBtn.setOnClickListener {
            if(tabLayout.selectedTabPosition == 0){
                finish()
            }else {
                val tab: TabLayout.Tab? = tabLayout.getTabAt(tabLayout.selectedTabPosition - 1)
                tab?.select()
            }
        }

    }
    private fun post(){

        if(postPageAdapter.fragment1.isInitialized()){
            this.nameFood = postPageAdapter.fragment1.foodName.text.toString()
            this.listImageUri = postPageAdapter.fragment1.imageList
        }
        if(postPageAdapter.fragment2.isInitialized()){
            this.portion = postPageAdapter.fragment2.portion.text.toString()
            this.difficult = postPageAdapter.fragment2.difficult.rating.toString()
            this.prepTime =  postPageAdapter.fragment2.prepTime.text.toString()
        }
        if(postPageAdapter.fragment3.isInitialized()){
            this.ingredientList = postPageAdapter.fragment3.ingredientList
        }
        if(postPageAdapter.fragment4.isInitialized()){
            this.listStepLocal = postPageAdapter.fragment4.stepList
        }
        if(postPageAdapter.fragment5.isInitialized()){
            this.foodNote = postPageAdapter.fragment5.foodNote.text.toString()
        }
        upLoadImageFoodToFirebase()

        //finish()
    }



    private fun initPost(){
        Log.d("PostPage", "initPost")

        val newPostData = FirebaseFirestore.getInstance().collection("post").document()
        val postData = hashMapOf(
            "id" to newPostData.id,
            "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "images" to listImageUrlFoodFB,
            "nameFood" to nameFood,
            "description" to foodNote  ,
            "cookingTime" to prepTime,
            "servers" to portion,
            "level" to difficult,
            "ingredients" to ingredientList,
            "methods" to listStepFB,
            "favourites" to mutableListOf<String>(),
            "favouritesCount" to 0,
            "timePost" to LocalDateTime.now(),
            "share" to 0,
            "comments" to mutableListOf<Map<String, Any>>(),
            "timestamp"   to  Timestamp.now()
        )
        newPostData.set(postData)
        addIDPostToUser(newPostData.id)
    }

    @SuppressLint("ResourceType")
    private fun addIDPostToUser(IDNewPost: String){
        val updater = FirebaseFirestore.getInstance()
        updater.collection("user")
            .document("${FirebaseAuth.getInstance().currentUser?.email}")
            .update("post", FieldValue.arrayUnion(IDNewPost))

        Log.d("PostPage", "add ID successful")
        finish()

    }


    private fun upLoadImageFoodToFirebase() {

        Log.d("PostPage", "Loading...")

        val storageRef = FirebaseStorage.getInstance().reference
        if(listImageUri.isNotEmpty()){
            for (i in 0 until listImageUri.count()) {

                val ref = storageRef.child("post/${listImageUri[i].lastPathSegment}")
                val uploadTask = ref.putFile(listImageUri[i])
                Log.d("testuri", listImageUri[i].toString())

                uploadTask.addOnFailureListener {

                }.addOnSuccessListener {

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
                        listImageUrlFoodFB.add(task.result.toString());
                        //nếu đã get đủ url ảnh
                        if(listImageUrlFoodFB.count() == listImageUri.count()){
                            // initPost(listImageUrlFB)
                            //initPost()
                            uploadImageStepToFireBase()
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
        else{
            uploadImageStepToFireBase()
        }
    }
    private fun uploadImageStepToFireBase(){

        Log.d("PostPage", "Loading...2")

        val storageRef = FirebaseStorage.getInstance().reference

        if(listStepLocal.isNotEmpty()){
            for (i in 0 until this.listStepLocal.count()) {

                val ref = storageRef.child("post/${listStepLocal[i].image.lastPathSegment}")
                val uploadTask = ref.putFile(listStepLocal[i].image)

                uploadTask.addOnFailureListener {

                }.addOnSuccessListener {

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

                        // listImageUrlFoodFB.add(task.result.toString());
                        listStepFB.add(Step(task.result, listStepLocal[i].step))
                        //nếu đã get đủ url ảnh
                        if(listStepFB.count() == listStepLocal.count()){
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
        else{
            initPost()
        }
    }
}


