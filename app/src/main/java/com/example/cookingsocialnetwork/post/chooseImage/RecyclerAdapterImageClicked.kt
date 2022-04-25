package com.example.cookingsocialnetwork.post.chooseImage

import android.bluetooth.BluetoothClass
import android.icu.text.DateTimePatternGenerator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cookingsocialnetwork.R
import com.squareup.picasso.Picasso


class RecyclerAdapterImageClicked(private var mphotosUriLiveData: MutableLiveData<MutableList<Uri>>) :
    RecyclerView.Adapter<RecyclerAdapterImageClicked.ViewHolder>(){



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterImageClicked.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item_clicked_image_choosed, parent, false)
//        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
//        params.height = parent.minimumHeight
//        view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterImageClicked.ViewHolder, position: Int) {
        val itemImage = mphotosUriLiveData.value?.get(position)!!
        holder.bindImage(itemImage)
    }

    override fun getItemCount(): Int {
        return mphotosUriLiveData.value?.size!!
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemImage: ImageView = itemView.findViewById(R.id.image_clicked_view)
        private var butDelete: Button = itemView.findViewById(R.id.btn_deleteImageChoosed)

        fun bindImage(uri: Uri) {
            Picasso.get().load(uri).into(this.itemImage)

        }
    }
}
