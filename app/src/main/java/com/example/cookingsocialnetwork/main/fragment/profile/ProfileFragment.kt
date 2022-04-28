package com.example.cookingsocialnetwork.main.fragment.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentProfileBinding
import com.example.cookingsocialnetwork.setting.SettingPage
import com.example.cookingsocialnetwork.setting.changeProfile.SettingChangeProfile

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container,false)
        val factory = ProfileViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.openSetting.setOnClickListener{
            val settingPage = Intent(activity, SettingPage::class.java)
            startActivity(settingPage)
        }

        viewModel.getUser.observe(viewLifecycleOwner)
        { user->
            DownloadImageFromInternet(binding.userAvatar).execute(user.avatar)
            binding.userAvatar.setImageURI(Uri.parse(user.avatar))
            binding.post.text = user.post.size.toString()
            binding.follower.text = user.followers.size.toString()
            binding.following.text = user.following.size.toString()
        }

        return binding.root
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
            }
            return image
        }
        @Deprecated("Deprecated in Java", ReplaceWith("imageView.setImageBitmap(result)"))
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}