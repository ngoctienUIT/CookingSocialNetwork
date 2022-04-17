package com.example.cookingsocialnetwork.model

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.google.firebase.firestore.FirebaseFirestore

class ListAdapterUser(context: Activity, private var userArrayList: MutableList<User>, var myData: User):
    ArrayAdapter<User>(context, R.layout.list_item_user , userArrayList) {
    @SuppressLint("ViewHolder", "SetTextI18n", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_user, null)

        val imageView: ImageView = view.findViewById(R.id.avatarUser)
        val name: TextView = view.findViewById(R.id.name)
        val info: TextView = view.findViewById(R.id.info)
        val follow: Button = view.findViewById(R.id.follow)

        if (myData.username.compareTo(userArrayList[position].username)==0) follow.visibility = View.GONE
        else {
            var following = myData.following
            var follower = userArrayList[position].followers

            val checkFollow = myData.following.indexOf(userArrayList[position].username) != -1

            if (checkFollow) follow.text = "Unfollow"

            follow.setOnClickListener()
            {

                if (!checkFollow) {
                    following.add(userArrayList[position].username)
                    follower.add(myData.username)
                } else {
                    following.remove(userArrayList[position].username)
                    follower.remove(myData.username)
                }

                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(myData.username)
                    .update("following", following)

                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(userArrayList[position].username)
                    .update("followers", follower)
            }
        }

        DownloadImageFromInternet(view.findViewById(R.id.avatarUser)).execute(userArrayList[position].avatar)
        imageView.setImageURI(Uri.parse(userArrayList[position].avatar))
        name.text = userArrayList[position].name
        info.text = "${userArrayList[position].followers.size} follower · ${userArrayList[position].post.size} bài viết"

        return view
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