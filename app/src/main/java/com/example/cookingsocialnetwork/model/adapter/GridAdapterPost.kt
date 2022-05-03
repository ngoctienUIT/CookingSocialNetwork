package com.example.cookingsocialnetwork.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post

class GridAdapterPost(var context: Context, var listPost: MutableList<Post>): BaseAdapter() {
    override fun getCount(): Int = listPost.size

    override fun getItem(positon: Int): Any = listPost[positon]

    override fun getItemId(positon: Int): Long = positon.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(positon: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.grid_item_post, null)
        var imagePost: ImageView = view.findViewById(R.id.image_post)
        var namePost : TextView = view.findViewById(R.id.name_post)
        var avatar: ImageView = view.findViewById(R.id.avatar)
        var name: TextView = view.findViewById(R.id.name)

//        namePost.text = listPost[positon].nameFood

        return view
    }
}