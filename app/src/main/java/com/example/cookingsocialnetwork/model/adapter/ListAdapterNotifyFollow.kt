package com.example.cookingsocialnetwork.model.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ListAdapterNotifyFollow(context: Activity, private var followNotify: MutableList<Notify>):
    ArrayAdapter<Notify>(context, R.layout.favorite_notify_item , followNotify) {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.follow_notify_item, null)

        val imageView: ImageView = view.findViewById(R.id.avatarUser)
        val name: TextView = view.findViewById(R.id.name)
        val time: TextView = view.findViewById(R.id.time)

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(followNotify[position].name)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                name.text = info["name"].toString()
                val avatar = info["avatar"].toString()
                Picasso.get().load(avatar).into(imageView)
            }

        time.text = followNotify[position].time

        return view
    }
}