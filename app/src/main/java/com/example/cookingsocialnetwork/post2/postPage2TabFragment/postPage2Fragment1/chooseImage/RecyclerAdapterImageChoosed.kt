package com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


import androidx.recyclerview.widget.RecyclerView
import coil.api.load

import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.post2.model.IngredientAdapter


class RecyclerAdapterImageChoosed(private var mPhotosUris: MutableList<Uri>) : RecyclerView.Adapter<RecyclerAdapterImageChoosed.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_formimage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemImageUri = mPhotosUris[position]
        holder.bindImage(itemImageUri)
    }

    override fun getItemCount(): Int {
        return mPhotosUris.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

       private var itemImage: ImageView = itemView.findViewById(R.id.item_ImageChoose)


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
