package com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import io.reactivex.disposables.CompositeDisposable

class TrendingAdapter : PagingDataAdapter<Post, TrendingAdapter.TrendingSlideViewHolder>(
    Companion) {

    companion object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingSlideViewHolder {
        return TrendingSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.trending_slider,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingSlideViewHolder, position: Int) {
        val post = getItem(position)
        post?.let { holder.bind(it) }
    }

    override fun onViewRecycled(holder: TrendingAdapter.TrendingSlideViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
           // userName.text = ""
            foodName.text = ""
            foodImage.setImageDrawable(Drawable.createFromPath("@drawable/food_picker"))
            heartPost.text = ""
            // userImage.setImageDrawable(Drawable.createFromPath("@color/black"))
            //comment.text = "";
           // heart.text = ""
            //rating.numStars = 0

            viewHolderDisposables.clear()
        }
    }


    inner class TrendingSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val viewHolderDisposables = CompositeDisposable()
        val foodImage = view.findViewById<ImageView>(R.id.trendingFoodPicture)!!
        val foodName = view.findViewById<TextView>(R.id.trendingNameFood)!!
        val heartPost = view.findViewById<TextView>(R.id.heartTrending)!!

        fun bind(post : Post) {
            post.let {
               /* FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(post.owner)
                    .get()
                    .addOnSuccessListener { userSnap ->
                        val user = User()
                        user.getData(userSnap)
                    }*/
                foodImage.load(post.images[0])
                foodName.text = post.nameFood
                heartPost.text = post.favourites.size.toString()
            } to viewHolderDisposables
        }
    }
}
