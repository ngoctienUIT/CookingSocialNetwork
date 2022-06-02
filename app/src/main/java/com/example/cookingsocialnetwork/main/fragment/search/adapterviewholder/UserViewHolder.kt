package com.example.cookingsocialnetwork.main.fragment.search.adapterviewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var usernameView: TextView? = null
    private var avatarView: ImageView? = null
    private var infoView: TextView? = null
    private var follow: Button? = null
    var user: User? =null
    var onClickItem : ((String)->Unit)? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        usernameView = view.get()?.findViewById(R.id.username)
        infoView = view.get()?.findViewById(R.id.info)
        avatarView=view.get()?.findViewById(R.id.avatarUser)
        follow = view.get()?.findViewById(R.id.follow)
    }

    private fun setListener()
    {

    }

    @SuppressLint("SetTextI18n")
    fun updateView()
    {
        findView()
        usernameView?.text = user?.username
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(user!!.username)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                val followers = data["followers"] as MutableList<String>
                val post = data["post"] as MutableList<String>
                nameView?.text = info["name"].toString()
                val avatar = info["avatar"].toString()
                avatarView?.let { image ->
                    Picasso.get().load(avatar).into(image)
                }
                infoView?.text = "${followers.size} follower . ${post.size} bài viết"
            }
        createFollowFunction()
    }

    private fun createFollowFunction()
    {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                val myData = User()
                myData.getData(it)
                val following = myData.following
                val follower = user?.followers
                val checkFollow = myData.following.indexOf(user?.username) != -1
                if (checkFollow) follow?.text = "Unfollow"
                follow?.setOnClickListener()
                {
                    if (!checkFollow) {
                        following.add(user!!.username)
                        follower!!.add(myData.username)
                    } else {
                        following.remove(user?.username)
                        follower!!.remove(myData.username)
                    }

                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(myData.username)
                        .update("following", following)

                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(user!!.username)
                        .update("followers", follower)
                }
            }
    }
}