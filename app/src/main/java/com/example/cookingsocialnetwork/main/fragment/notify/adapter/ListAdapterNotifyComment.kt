package com.example.cookingsocialnetwork.main.fragment.notify.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ListAdapterNotifyComment(context: Activity, private var commentNotify: MutableList<Notify>):
    ArrayAdapter<Notify>(context, R.layout.comment_notify_item , commentNotify) {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.comment_notify_item, null)

        val imageView: ImageView = view.findViewById(R.id.avatarUser)
        val name: TextView = view.findViewById(R.id.name)
        val time: TextView = view.findViewById(R.id.time)
        val content: TextView = view.findViewById(R.id.content)
        val postView: ImageView = view.findViewById(R.id.post)

        time.text = commentNotify[position].time.dataTime
        if (commentNotify[position].type.compareTo("comment") == 0)
            content.text = "Đã bình luận: " + commentNotify[position].content
        else content.text = "Đã thích bình luận: " + commentNotify[position].content
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(commentNotify[position].name)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                name.text = info["name"].toString()
                val avatar = info["avatar"].toString()
                imageView.load(avatar)
//                Picasso.get().load(avatar).into(imageView)
            }

        FirebaseFirestore.getInstance()
            .collection("post")
            .document(commentNotify[position].id)
            .get()
            .addOnSuccessListener {
                val post = Post()
                post.getData(it)
                postView.load(post.images[0])
//                Picasso.get().load(post.images[0]).into(postView)
            }

        return view
    }
}