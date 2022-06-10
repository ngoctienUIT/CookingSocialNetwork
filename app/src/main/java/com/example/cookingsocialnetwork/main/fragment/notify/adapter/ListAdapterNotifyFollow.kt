package com.example.cookingsocialnetwork.main.fragment.notify.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.FollowControl
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
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
        val follow: Button = view.findViewById(R.id.follow)

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(followNotify[position].name)
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
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener { myUser ->
                val myData = User()
                myData.getData(myUser)

                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(followNotify[position].name)
                    .get()
                    .addOnSuccessListener { dataUser ->
                        val userData = User()
                        userData.getData(dataUser)
                        val checkFollow = myData.following.indexOf(userData.username) != -1

                        if (checkFollow) follow.text = "Unfollow"
                        else follow.text = "Follow lại"
                    }
            }

        follow.setOnClickListener()
        {
            FollowControl.follow(followNotify[position].name)
        }

        time.text = followNotify[position].time.dataTime

        return view
    }
}