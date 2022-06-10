package com.example.cookingsocialnetwork.main.fragment.notify.adapterviewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.FollowControl
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var contentView: TextView? = null
    private var timeView: TextView? = null
    private var avatarView: ImageView? = null
    private var followBtn: Button? = null
    var follow: Notify? = null

    init {
        findView()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        contentView = view.get()?.findViewById(R.id.content)
        timeView = view.get()?.findViewById(R.id.time)
        avatarView = view.get()?.findViewById(R.id.avatarUser)
        followBtn = view.get()?.findViewById(R.id.follow)
    }

    fun updateView() {
        findView()
        timeView?.text = follow?.time?.dataTime
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(follow!!.name)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                nameView?.text = info["name"].toString()
                val avatar = info["avatar"].toString()
                avatarView?.let { image ->
                    image.load(avatar)
//                    Picasso.get().load(avatar).into(image)
                }
            }

        followBtn?.setOnClickListener()
        {
            FollowControl.follow(follow!!.name)
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
                    .document(follow!!.name)
                    .get()
                    .addOnSuccessListener { dataUser ->
                        val userData = User()
                        userData.getData(dataUser)
                        val checkFollow = myData.following.indexOf(userData.username) != -1

                        if (checkFollow) followBtn?.text = "Unfollow"
                        else followBtn?.text = "Follow lại"
                    }
            }
    }
}