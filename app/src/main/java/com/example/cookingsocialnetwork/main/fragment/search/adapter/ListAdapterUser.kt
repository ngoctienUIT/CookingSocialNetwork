package com.example.cookingsocialnetwork.main.fragment.search.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ListAdapterUser(context: Activity, private var userArrayList: MutableList<User>, var myData: User):
    ArrayAdapter<User>(context, R.layout.list_item_user , userArrayList) {
    @SuppressLint("ViewHolder", "SetTextI18n", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_user, null)

        val imageView: ImageView = view.findViewById(R.id.avatarUser)
        val name: TextView = view.findViewById(R.id.name)
        val username: TextView = view.findViewById(R.id.username)
        val info: TextView = view.findViewById(R.id.info)
        val follow: Button = view.findViewById(R.id.follow)

        if (myData.username.compareTo(userArrayList[position].username)==0) follow.visibility = View.GONE
        else {
            val following = myData.following
            val follower = userArrayList[position].followers

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

        Picasso.get().load(userArrayList[position].avatar).into(imageView)
        imageView.setImageURI(Uri.parse(userArrayList[position].avatar))
        name.text = userArrayList[position].name
        username.text = userArrayList[position].username
        info.text = "${userArrayList[position].followers.size} follower · ${userArrayList[position].post.size} bài viết"

        return view
    }
}