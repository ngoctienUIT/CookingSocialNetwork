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
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cookingsocialnetwork.model.LanguageManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.mainActivity.MainActivity
import com.example.cookingsocialnetwork.setting.about.AboutPage
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
            val selectedImageUri: Uri? = result.data?.data
            if (null != selectedImageUri) {
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
            if (!switch_dark_mode.isChecked) darkMode(2)
            else darkMode(1)
            switch_dark_mode.isChecked = !switch_dark_mode.isChecked
        }

        switch_dark_mode.setOnClickListener()
        {
            if (switch_dark_mode.isChecked) darkMode(2)
            else darkMode(1)
        }

        info.setOnClickListener()
        {
            val aboutPage = Intent(this, AboutPage::class.java)
            startActivity(aboutPage)
        }
    }

    private fun darkMode(mode: Int)
    {
        var sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        if (mode == 2) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else if (mode == 1) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        with(sharePref.edit())
        {
            putInt("darkMode", mode)
            commit()
        }
    }

    fun imageChooser() {
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

        var sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
        val check = sharePref.getString("language", "vi")

        if (check == "vi") dialog.radio_group_language.check(R.id.radio_vietnam)
        else if (check == "en") dialog.radio_group_language.check(R.id.radio_english)

        dialog.radio_english.setOnClickListener()
        {
            if (check != "en") changeLanguage("en")
        }

        dialog.radio_vietnam.setOnClickListener()
        {
            if (check != "vi") changeLanguage("vi")
        }

        dialog.vietnam.setOnClickListener()
        {
            if (check != "vi") changeLanguage("vi")
        }

        dialog.english.setOnClickListener() {
            if (check != "en") changeLanguage("en")
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }

    private fun changeLanguage(language: String)
    {
        var sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
        val lang = LanguageManager(this)
        when (language)
        {
            "vi" -> lang.updateResource("vi")
            "en" -> lang.updateResource("en-US")
        }
        with(sharePref.edit())
        {
            putString("language", language)
            commit()
        }
        val reopen = Intent(this, MainActivity::class.java)
        startActivity(reopen)
    }
}