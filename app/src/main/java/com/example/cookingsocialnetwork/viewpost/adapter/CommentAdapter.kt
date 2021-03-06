package com.example.cookingsocialnetwork.viewpost.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.NotifyControl
import com.example.cookingsocialnetwork.model.data.Time
import com.example.cookingsocialnetwork.model.data.User
import com.example.cookingsocialnetwork.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.ref.WeakReference

class CommentAdapter(private var comments: MutableList<Map<String, Any>>, private var id: String, listener: MyListListener)
    : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var listener: MyListListener? = null

    init {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.comment = comments[position]
        val favouriteList = comments[position]["favourite"] as MutableList<String>
        holder.itemView.setOnLongClickListener()
        {
            listener?.onItemLongPressed(position)
            return@setOnLongClickListener true
        }
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
                        if (check) NotifyControl.addNotify(item["userName"] as String, item["content"] as String, id, "like_comment")
                        else NotifyControl.removeNotify(item["userName"] as String, item["content"] as String, id, "like_comment")
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
        holder.name?.setOnClickListener()
        {
            val profile = Intent(holder.itemView.context, ProfileActivity::class.java)
            profile.putExtra("user_name", comments[position]["userName"] as String)
            holder.itemView.context.startActivity(profile)
        }
        holder.userAvatar?.setOnClickListener()
        {
            val profile = Intent(holder.itemView.context, ProfileActivity::class.java)
            profile.putExtra("user_name", comments[position]["userName"] as String)
            holder.itemView.context.startActivity(profile)
        }
        holder.updateView()
    }

    override fun getItemCount(): Int = comments.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private var view: WeakReference<View> = WeakReference(itemView)
        var name: TextView? = null
        private var content: TextView? = null
        private var time: TextView? = null
        private var favourite: TextView? = null
        var userAvatar: ImageView? = null
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
                    userAvatar?.load(user.avatar)
//                    Picasso.get().load(user.avatar).into(userAvatar)
                    name?.text = user.name
                }

            if (favouriteList.indexOf(FirebaseAuth.getInstance().currentUser?.email.toString()) > -1)
                iconFavourite?.setImageDrawable(ResourcesCompat.getDrawable(view.get()!!.resources, R.drawable.ic_favorite, null))
            else iconFavourite?.setImageDrawable(ResourcesCompat.getDrawable(view.get()!!.resources, R.drawable.ic_favorite_border, null))
        }
    }
}