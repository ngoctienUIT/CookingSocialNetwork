package com.example.cookingsocialnetwork.main.fragment.home.listPosts.randomPosts

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.main.fragment.home.listPosts.recentPosts.RealtimePost
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.example.cookingsocialnetwork.newviewpost.ViewPost
import com.example.cookingsocialnetwork.viewpost.ViewFullPost
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.disposables.CompositeDisposable

class RandomPostAdapter : PagingDataAdapter<Post, RandomPostAdapter.PostViewHolder>(
    Companion) {

    companion object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_form,
            parent,
            false
        )
        return PostViewHolder(view)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val  post = getItem(position)
        post?.let { holder.bind(it) }

        holder.itemView.setOnClickListener{
            val idPostClicked = post?.id
            val context = holder.itemView.context
            if(idPostClicked != ""){
                val fullPost = Intent(context, ViewPost::class.java)
                fullPost.putExtra("id_post", idPostClicked)
                context.startActivity(fullPost)
            }
        }
    }

    override fun onViewRecycled(holder: PostViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            userName.text = ""
            foodName.text = ""
            foodImage.setImageDrawable(Drawable.createFromPath("@drawable/food_picker"))
           // userImage.setImageDrawable(Drawable.createFromPath("@color/black"))
            comment.text = "";
            heart.text = ""
            rating.numStars = 0
            viewHolderDisposables.clear()
        }
    }


    inner class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewHolderDisposables = CompositeDisposable()

       // val userImage : ImageView = itemView.findViewById(R.id.user_image)
        val rating : RatingBar = itemView.findViewById(R.id.food_diff_random_post)
        val comment : TextView = itemView.findViewById(R.id.comment_random_post)
        val heart : TextView = itemView.findViewById(R.id.heart_random_post)
        val userName : TextView = itemView.findViewById(R.id.user_random_post)
        val foodImage : ImageView = itemView.findViewById(R.id.food_image_random_post)
        val foodName : TextView = itemView.findViewById(R.id.food_name_random_post)



        fun bind(post : Post) {
            post.let {
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(post.owner)
                    .get()
                    .addOnSuccessListener { userSnap ->
                        val user = User()
                        user.getData(userSnap)
                        //userImage.load(user.avatar)
                        userName.text = user.name
                    }
                foodImage.load(post.images[0])
                foodName.text = post.nameFood
                rating.numStars = post.level.toInt()
                comment.text = post.comments.size.toString()
                heart.text = post.favourites.size.toString()
            } to viewHolderDisposables

        }

    }
}
