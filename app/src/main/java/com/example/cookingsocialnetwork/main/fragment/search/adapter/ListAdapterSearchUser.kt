package com.example.cookingsocialnetwork.main.fragment.search.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.User
import com.squareup.picasso.Picasso

class ListAdapterSearchUser (private var dialog: Dialog, private var userList: MutableList<User>):
    ArrayAdapter<User>(dialog.context, R.layout.list_search_history_item, userList) {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_search_user_item, null)

        val name: TextView = view.findViewById(R.id.name)
        val username: TextView = view.findViewById(R.id.username)
        val avatar: ImageView = view.findViewById(R.id.avatarUser)

        name.text = userList[position].name
        username.text = userList[position].username
        Picasso.get().load(userList[position].avatar).into(avatar)

        return view
    }
}