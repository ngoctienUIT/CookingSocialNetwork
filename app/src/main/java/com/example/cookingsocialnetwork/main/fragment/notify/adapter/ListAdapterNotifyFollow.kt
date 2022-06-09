package com.example.cookingsocialnetwork.main.fragment.notify.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.Time
import com.example.cookingsocialnetwork.model.data.User
import com.example.cookingsocialnetwork.model.service.SendNotify
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.time.LocalDateTime

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
                Picasso.get().load(avatar).into(imageView)
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
            follow(followNotify[position].name)
        }

        time.text = followNotify[position].time.dataTime

        return view
    }

    fun follow(username: String) {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener { myUser ->
                val myData = User()
                myData.getData(myUser)

                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(username)
                    .get()
                    .addOnSuccessListener { dataUser ->
                        val userData = User()
                        userData.getData(dataUser)

                        val following = myData.following
                        val follower = userData.followers

                        val checkFollow = myData.following.indexOf(username) != -1

                        if (!checkFollow) {
                            following.add(userData.username)
                            follower.add(myData.username)
                            addNotify(userData.username, myData.username)
                            SendNotify.sendMessage(
                                "",
                                FirebaseAuth.getInstance().currentUser?.email.toString(),
                                userData.username,
                                "",
                                "follow",
                                "notification"
                            )
                        } else {
                            following.remove(userData.username)
                            follower.remove(myData.username)
                            removeNotify(userData.username)
                        }

                        FirebaseFirestore.getInstance()
                            .collection("user")
                            .document(myData.username)
                            .update("following", following)

                        FirebaseFirestore.getInstance()
                            .collection("user")
                            .document(username)
                            .update("followers", follower)

                    }
            }
    }

    private fun addNotify(username: String, myUser: String)
    {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(username)
            .get()
            .addOnSuccessListener { snapshot ->
                val data = snapshot.data
                val notifyData = data?.get("notify") as MutableList<Map<String, Any>>

                val notify = hashMapOf(
                    "content" to "",
                    "id" to "",
                    "name" to myUser,
                    "status" to 1,
                    "time" to LocalDateTime.now(),
                    "type" to "follow"
                )

                notifyData.add(notify)
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(username)
                    .update("notify", notifyData)
            }
    }

    private fun removeNotify(username: String)
    {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(username)
            .get()
            .addOnSuccessListener { snapshot ->
                val data = snapshot.data
                val notifyData = data?.get("notify") as MutableList<Map<String, Any>>
                var count = 0;
                val myNotify = Notify(FirebaseAuth.getInstance().currentUser?.email.toString(), "", "follow", 1, Time())
                myNotify.content = ""
                notifyData.forEach()
                {
                    val notify = Notify()
                    notify.getData(it)
                    if (notify.compareTo(myNotify)) return@forEach
                    count++
                }

                notifyData.removeAt(count)
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(username)
                    .update("notify", notifyData)
            }
    }
}