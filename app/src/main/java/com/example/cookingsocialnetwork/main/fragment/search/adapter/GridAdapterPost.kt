package com.example.cookingsocialnetwork.main.fragment.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.firestore.FirebaseFirestore

class GridAdapterPost(var context: Context, var listPost: MutableList<Post>): BaseAdapter() {
    override fun getCount(): Int = listPost.size

    override fun getItem(positon: Int): Any = listPost[positon]

    override fun getItemId(positon: Int): Long = positon.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(positon: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.grid_item_post, null)
        val imagePost: ImageView = view.findViewById(R.id.image_post)
        val namePost : TextView = view.findViewById(R.id.name_post)
        val avatar: ImageView = view.findViewById(R.id.avatar)
        val name: TextView = view.findViewById(R.id.name)
        val favorite: TextView = view.findViewById(R.id.favorite)

        favorite.text = listPost[positon].favourites.size.toString()
        namePost.text = listPost[positon].nameFood
        imagePost.load(listPost[positon].images[0])
//        Picasso.get().load(listPost[positon].images[0]).into(imagePost)

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(listPost[positon].owner)
            .get()
            .addOnSuccessListener {
                val user = User()
                user.getData(it)
                name.text = user.name
                avatar.load(user.avatar)
//                Picasso.get().load(user.avatar).into(avatar)
            }

        return view
    }
}