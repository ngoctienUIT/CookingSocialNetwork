package com.example.cookingsocialnetwork.setting

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingsocialnetwork.LanguageManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.mainActivity.MainActivity
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfile
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_setting_page.*
import kotlinx.android.synthetic.main.layout_bottomsheet.*
import kotlinx.android.synthetic.main.layout_dialog_choose_language.*

class SettingPage : AppCompatActivity() {
    private var imageChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Get the url of the image from data
            val selectedImageUri: Uri? = result.data?.data
            if (null != selectedImageUri) {
                // update the preview image in the layout
                IVPreviewImage?.setImageURI(selectedImageUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_page)

        supportActionBar?.hide()
        back_setting.setOnClickListener()
        {
            finish()
        }

        changelanguage.setOnClickListener{
//            openChooseLanguageDialog(Gravity.CENTER)
            showDialog()
        }

        logout.setOnClickListener()
        {
            logOut()
        }

        btn_account.setOnClickListener()
        {
            val settingChangeProfile = Intent(this, SettingChangeProfile::class.java)
            startActivity(settingChangeProfile)
        }
    }

    /*private fun openChooseLanguageDialog(gravity: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE )
        dialog.setContentView(R.layout.layout_dialog_choose_language)
        dialog.setCancelable(false)
        val window = dialog.window ?: return
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        dialog.setLanguageEnglish.setOnClickListener()
        {
            val lang = LanguageManager(this)
            lang.updateResource("en-US")
            val reopen = Intent(this, MainActivity::class.java)
            startActivity(reopen)
            finish()
        }

        dialog.setLanguageVietnamese.setOnClickListener()
        {
            val lang = LanguageManager(this)
            lang.updateResource("vi")
            val reopen = Intent(this, MainActivity::class.java)
            startActivity(reopen)
            finish()
        }
        dialog.show()
    }*/

//    private var SELECT_PICTURE = 200

    fun imageChooser() {
        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        imageChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
//startActivityForResult không được sử dụng nữa
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }

    private var IVPreviewImage: ImageView? = null

// onActivityResult không còn được sử dụng nữa
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                val selectedImageUri: Uri? = data?.data
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                    IVPreviewImage?.setImageURI(selectedImageUri)
//                }
//            }
//        }
//    }

    private fun logOut()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()

        Firebase.auth.signOut()
        val i = Intent(this, MainActivity::class.java)
        // set the new task and clear flags
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun showDialog()
    {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_bottomsheet)

        dialog.vietnam.setOnClickListener()
        {
            val lang = LanguageManager(this)
            lang.updateResource("vi")
            val reopen = Intent(this, MainActivity::class.java)
            startActivity(reopen)
            finish()
        }

        dialog.english.setOnClickListener() {
            val lang = LanguageManager(this)
            lang.updateResource("en-US")
            val reopen = Intent(this, MainActivity::class.java)
            startActivity(reopen)
            finish()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }
}