package com.example.cookingsocialnetwork.setting

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

        var sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        val check = sharePref.getInt("darkMode", 2)
        switch_dark_mode.isChecked = check != 1

        back_setting.setOnClickListener()
        {
            finish()
        }

        changelanguage.setOnClickListener{
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

        dark_mode.setOnClickListener()
        {
            if (!switch_dark_mode.isChecked) startDarkMode()
            else offDarkMode()
            switch_dark_mode.isChecked = !switch_dark_mode.isChecked
        }

        switch_dark_mode.setOnClickListener()
        {
            if (switch_dark_mode.isChecked) startDarkMode()
            else offDarkMode()
        }
    }

    private fun startDarkMode()
    {
        var sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        with(sharePref.edit())
        {
            putInt("darkMode", androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES)
            commit()
        }
    }

    fun offDarkMode()
    {
        var sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        with(sharePref.edit())
        {
            putInt("darkMode", androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO)
            commit()
        }
    }

    fun imageChooser() {
        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        imageChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
    }

    private var IVPreviewImage: ImageView? = null

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
            reopen.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(reopen)
        }

        dialog.english.setOnClickListener() {
            val lang = LanguageManager(this)
            lang.updateResource("en-US")
            val reopen = Intent(this, MainActivity::class.java)
            reopen.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(reopen)
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }
}