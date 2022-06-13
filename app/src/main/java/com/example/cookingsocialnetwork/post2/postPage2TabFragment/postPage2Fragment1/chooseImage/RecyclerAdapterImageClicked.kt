package com.example.cookingsocialnetwork.post2.postPage2TabFragment.postPage2Fragment1.chooseImage

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load

import com.example.cookingsocialnetwork.R


class RecyclerAdapterImageClicked(private var mPhotoUris: MutableList<Uri>,
private var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<RecyclerAdapterImageClicked.ViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item_clicked_image_choosed, parent, false)
//        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
//        params.height = parent.minimumHeight
//        view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemImage = mPhotoUris[position]
        holder.bindImage(itemImage)

        holder.butDelete.setOnClickListener {
            itemClickListener.onItemRemoveClick(itemImage, holder.absoluteAdapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return mPhotoUris.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemImage: ImageView = itemView.findViewById(R.id.image_clicked_view)
        var butDelete: Button = itemView.findViewById(R.id.btn_deleteImageChoosed)

        fun bindImage(uri: Uri) {
            /*
            Picasso.get().load(uri).into(this.itemImage)
            Glide.with(itemView).load(uri).into(itemImage)
            */
            itemImage.load(uri)
        }

    }

}
