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
            if (!switch_dark_mode.isChecked) onDarkMode()
            else offDarkMode()
            switch_dark_mode.isChecked = !switch_dark_mode.isChecked
        }

        switch_dark_mode.setOnClickListener()
        {
            if (switch_dark_mode.isChecked) onDarkMode()
            else offDarkMode()
        }
    }

    private fun onDarkMode()
    {
        var sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        with(sharePref.edit())
        {
            putInt("darkMode", androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES)
            commit()
        }
    }

    private fun offDarkMode()
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
            if (check != "en") chooseEnglish()
        }

        dialog.radio_vietnam.setOnClickListener()
        {
            if (check != "vi") chooseVietNam()
        }

        dialog.vietnam.setOnClickListener()
        {
            if (check != "vi") chooseVietNam()
        }

        dialog.english.setOnClickListener() {
            if (check != "en") chooseEnglish()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }

    private fun chooseVietNam()
    {
        var sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
        val lang = LanguageManager(this)
        lang.updateResource("vi")
        with(sharePref.edit())
        {
            putString("language", "vi")
            commit()
        }
        val reopen = Intent(this, MainActivity::class.java)
        startActivity(reopen)
    }

    private fun chooseEnglish()
    {
        var sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
        val lang = LanguageManager(this)
        lang.updateResource("en-US")
        with(sharePref.edit())
        {
            putString("language", "en")
            commit()
        }
        val reopen = Intent(this, MainActivity::class.java)
        startActivity(reopen)
    }
}