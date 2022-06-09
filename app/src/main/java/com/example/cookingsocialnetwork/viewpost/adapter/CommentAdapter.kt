package com.example.cookingsocialnetwork.viewpost.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.Time
import com.example.cookingsocialnetwork.model.data.User
import com.example.cookingsocialnetwork.model.service.SendNotify
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference
import java.time.LocalDateTime

class CommentAdapter(private var comments: MutableList<Map<String, Any>>, private var id: String)
    : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.comment = comments[position]
        val favouriteList = comments[position]["favourite"] as MutableList<String>
        holder.btnFavourite?.setOnClickListener()
        {
            var check = false
            if (favouriteList.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString()) > -1)
                favouriteList.remove(FirebaseAuth.getInstance().currentUser?.email.toString())
            else {
                favouriteList.add(FirebaseAuth.getInstance().currentUser?.email.toString())
                check = true
            }
            val commentList: MutableList<Map<String, Any>> = mutableListOf()
            for ((count, item) in comments.withIndex()) {
                if (count != position) commentList.add(item)
                else {
                    if (FirebaseAuth.getInstance().currentUser?.email.toString().compareTo(item["userName"] as String) != 0) {
                        if (check) addNotify(item["content"] as String, item["userName"] as String)
                        else removeNotify(item["content"] as String, item["userName"] as String)
                    }
                    val comment = hashMapOf(
                        "content" to item["content"],
                        "favourite" to favouriteList,
                        "time" to item["time"],
                        "userName" to item["userName"]
                    ) as Map<String, Any>
                    commentList.add(comment)
                }
            }
            FirebaseFirestore.getInstance()
                .collection("post")
                .document(id)
                .update("comments", commentList)
        }
        holder.updateView()
    }

    override fun getItemCount(): Int = comments.size

    private fun removeNotify(content: String, username: String)
    {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(username)
            .get()
            .addOnSuccessListener { snapshot ->
                val data = snapshot.data
                val notifyData = data?.get("notify") as MutableList<Map<String, Any>>
                var count = 0
                val myNotify = Notify(FirebaseAuth.getInstance().currentUser?.email.toString(), id, "like_comment", 1, Time())
                myNotify.content = content
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

    private fun addNotify(content: String, username: String)
    {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(username)
            .get()
            .addOnSuccessListener { snapshot ->
                val data = snapshot.data
                val notifyData = data?.get("notify") as MutableList<Map<String, Any>>

                val notify = hashMapOf(
                    "content" to content,
                    "id" to id,
                    "name" to FirebaseAuth.getInstance().currentUser?.email.toString(),
//                    "status" to 1,
                    "time" to LocalDateTime.now(),
                    "type" to "like_comment"
                )
                SendNotify.sendMessage(
                    content,
                    FirebaseAuth.getInstance().currentUser?.email.toString(),
                    username,
                    id,
                    "like_comment",
                    "notification"
                )
                notifyData.add(notify)
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(username)
                    .update("notify", notifyData)
            }
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private var view: WeakReference<View> = WeakReference(itemView)
        private var name: TextView? = null
        private var content: TextView? = null
        private var time: TextView? = null
        private var favourite: TextView? = null
        private var userAvatar: ImageView? = null
        private var iconFavourite: ImageView? = null
        var btnFavourite: LinearLayout? = null
        var comment: Map<String, Any>? = null

        init {
            findView()
        }

        private fun findView() {
            name = view.get()?.findViewById(R.id.name)
            content = view.get()?.findViewById(R.id.content)
            time = view.get()?.findViewById(R.id.time)
            favourite = view.get()?.findViewById(R.id.favourite)
            userAvatar = view.get()?.findViewById(R.id.userAvatar)
            iconFavourite = view.get()?.findViewById(R.id.ico_favourite)
            btnFavourite = view.get()?.findViewById(R.id.btn_favourite)
        }

        fun updateView() {
            findView()
            content?.text = comment?.get("content").toString()
            val favouriteList = comment?.get("favourite") as MutableList<String>
            favourite?.text = favouriteList.size.toString()
            val timeData = Time()
            timeData.getTime(comment?.get("time") as Map<String, Any>)
            time?.text = timeData.dataTime
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(comment?.get("userName").toString())
                .get()
                .addOnSuccessListener {
                    val user = User()
                    user.getData(it)
                    Picasso.get().load(user.avatar).into(userAvatar)
                    name?.text = user.name
                }

            Log.w("favouriteList", favouriteList.toString())

            if (favouriteList.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString()) > -1)
                iconFavourite?.setImageDrawable(ResourcesCompat.getDrawable(view.get()!!.resources, R.drawable.ic_favorite, null))
            else iconFavourite?.setImageDrawable(ResourcesCompat.getDrawable(view.get()!!.resources, R.drawable.ic_favorite_border, null))
        }
    }
}