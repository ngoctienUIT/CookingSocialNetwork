package com.example.cookingsocialnetwork.model.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify

class ListAdapterNotifyFavorite(context: Activity, private var favoriteNotify: MutableList<Notify>):
    ArrayAdapter<Notify>(context, R.layout.favorite_notify_item , favoriteNotify) {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.favorite_notify_item, null)

        val imageView: ImageView = view.findViewById(R.id.avatarUser)
        val name: TextView = view.findViewById(R.id.name)
        val time: TextView = view.findViewById(R.id.time)

        name.text = favoriteNotify[position].name

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