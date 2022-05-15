package com.example.cookingsocialnetwork.post.chooseImage
import android.annotation.SuppressLint
import android.icu.number.NumberRangeFormatter.with
import android.icu.number.Scale
import android.net.Uri
import android.view.Gravity.FILL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData


import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.load
import com.bumptech.glide.Glide

import com.example.cookingsocialnetwork.R
import com.squareup.picasso.Picasso


class RecyclerAdapterImageChoosed(
    private var mPhotosUris: MutableList<Uri>,
    private val clickListener: () -> Unit ) :

    RecyclerView.Adapter<RecyclerAdapterImageChoosed.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterImageChoosed.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_formimage, parent, false)

        return ViewHolder(view) {
            clickListener()
        }
    }

    override fun onBindViewHolder(holder: RecyclerAdapterImageChoosed.ViewHolder, position: Int) {

        val itemImageUri = mPhotosUris[position]
        holder.bindImage(itemImageUri)
       // holder.itemImage.load(itemImageUri)

    }

    override fun getItemCount(): Int {
        return mPhotosUris.size
    }

    inner class ViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

       private var itemImage: ImageView = itemView.findViewById(R.id.item_ImageChoose)


        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
        fun bindImage(uri: Uri) {
            // Picasso.get().load(uri).into(this.itemImage)
          //  Glide.with(itemView.context).load(uri).into(itemImage)
          itemImage.load(uri){
              crossfade(300)
              scale(coil.size.Scale.FILL)
          }

        }
    }

}
