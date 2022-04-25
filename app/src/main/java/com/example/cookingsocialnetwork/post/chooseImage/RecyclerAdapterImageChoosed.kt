package com.example.cookingsocialnetwork.post.chooseImage
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData

import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.squareup.picasso.Picasso


class RecyclerAdapterImageChoosed(
    private var mphotosUriLiveData: MutableLiveData<MutableList<Uri>>,
    private val clickListener: () -> Unit ) :

    RecyclerView.Adapter<RecyclerAdapterImageChoosed.ViewHolder>(){

    inner class ViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(itemView){

        private var itemImage: ImageView = itemView.findViewById(R.id.item_ImageChoose)

        init {
            itemView.setOnClickListener{
                clickAtPosition(adapterPosition)
            }
        }
        fun bindImage(uri: Uri) {
            Picasso.get().load(uri).into(this.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_formimage, parent, false)

        return ViewHolder(view) {
            clickListener()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val itemImage = mphotosUriLiveData.value?.get(position)!!
        holder.bindImage(itemImage)
    }

    override fun getItemCount(): Int {
        return mphotosUriLiveData.value?.size!!
    }


}
