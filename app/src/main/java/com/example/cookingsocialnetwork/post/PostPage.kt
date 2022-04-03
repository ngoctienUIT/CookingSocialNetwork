package com.example.cookingsocialnetwork.post

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.cookingsocialnetwork.R
import com.google.api.Distribution
import kotlinx.android.synthetic.main.activity_post_page.*

class PostPage : AppCompatActivity() {
    private var imageUri: Uri? = null                // imageUrl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_page)
        supportActionBar?.hide()

    }

    fun setImage(view: View) {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100) // requestCode = 100
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data?.data
            food_image.setImageURI(imageUri)
        }
    }

    fun addIngredient(view: View) {
        val newLayout =  LinearLayout(this)
        newLayout.orientation = LinearLayout.HORIZONTAL
        newLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        val newText = EditText(this)
        newText.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1F)
        newLayout.addView(newText)

        val newCloseButton = Button(this)
        newCloseButton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1F)


        newLayout.addView(newCloseButton)
        ingredient.addView(newLayout)
        newCloseButton.setOnClickListener {
            deleteIngredient(view)
        }

    }
    fun deleteIngredient(view: View){
        ingredient.removeView(view.parent as View)
        TODO() //delete parent
    }
}