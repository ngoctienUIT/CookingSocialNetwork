package com.example.cookingsocialnetwork.setting

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cookingsocialnetwork.model.LanguageManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivitySettingPageBinding
import com.example.cookingsocialnetwork.databinding.LayoutBottomsheetBinding
import com.example.cookingsocialnetwork.mainActivity.MainActivity
import com.example.cookingsocialnetwork.setting.about.AboutPage
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfile
import com.example.cookingsocialnetwork.setting.report.ReportActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingPage : AppCompatActivity() {
    private lateinit var binding: ActivitySettingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val shareDarkMode = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        val checkDarkMode = shareDarkMode.getInt("darkMode", 2)
        binding.switchDarkMode.isChecked = checkDarkMode != 1

        val shareNotify = getSharedPreferences("ChangeNotify", MODE_PRIVATE)
        val checkNotify = shareNotify.getInt("notify", 1)
        binding.switchNotify.isChecked = checkNotify != 2

        Log.w("notify", checkNotify.toString())
        Log.w("dark", checkDarkMode.toString())

        binding.backSetting.setOnClickListener()
        {
            finish()
        }

        binding.btnReport.setOnClickListener()
        {
            val reportActivity = Intent(this, ReportActivity::class.java)
            startActivity(reportActivity)
        }

        binding.changelanguage.setOnClickListener{
            showDialog()
        }

        binding.logout.setOnClickListener()
        {
            logOut()
        }

        binding.btnAccount.setOnClickListener()
        {
            val settingChangeProfile = Intent(this, SettingChangeProfile::class.java)
            startActivity(settingChangeProfile)
        }

        binding.darkMode.setOnClickListener()
        {
            if (!binding.switchDarkMode.isChecked) darkMode(2)
            else darkMode(1)
            binding.switchDarkMode.isChecked = !binding.switchDarkMode.isChecked
        }

        binding.switchDarkMode.setOnClickListener()
        {
            if (binding.switchDarkMode.isChecked) darkMode(2)
            else darkMode(1)
        }

        binding.notify.setOnClickListener()
        {
            if (binding.switchNotify.isChecked) notify(2)
            else notify(1)
            binding.switchNotify.isChecked = !binding.switchNotify.isChecked
        }

        binding.switchNotify.setOnClickListener()
        {
            if (binding.switchNotify.isChecked) notify(1)
            else notify(2)
        }

        binding.info.setOnClickListener()
        {
            val aboutPage = Intent(this, AboutPage::class.java)
            startActivity(aboutPage)
        }
    }

    private fun notify(status: Int)
    {
        val sharePref = getSharedPreferences("ChangeNotify", MODE_PRIVATE)
        with(sharePref.edit())
        {
            putInt("notify", status)
            commit()
        }
    }

    private fun darkMode(mode: Int)
    {
        val sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        if (mode == 2) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else if (mode == 1) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        with(sharePref.edit())
        {
            putInt("darkMode", mode)
            commit()
        }
    }

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
        val dialog = Dialog(this)
        val dialogBinding: LayoutBottomsheetBinding = LayoutBottomsheetBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        val sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
        val check = sharePref.getString("language", "vi")

        if (check == "vi") dialogBinding.radioGroupLanguage.check(R.id.radio_vietnam)
        else if (check == "en") dialogBinding.radioGroupLanguage.check(R.id.radio_english)

        dialogBinding.radioEnglish.setOnClickListener()
        {
            if (check != "en") changeLanguage("en")
        }

        dialogBinding.radioVietnam.setOnClickListener()
        {
            if (check != "vi") changeLanguage("vi")
        }

        dialogBinding.vietnam.setOnClickListener()
        {
            if (check != "vi") changeLanguage("vi")
        }

        dialogBinding.english.setOnClickListener {
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
        val sharePref = getSharedPreferences("ChangeLanguage", MODE_PRIVATE)
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