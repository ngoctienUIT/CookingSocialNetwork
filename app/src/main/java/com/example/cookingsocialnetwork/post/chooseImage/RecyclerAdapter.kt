package com.example.cookingsocialnetwork.post.chooseImage
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val photosUri: MutableList<Uri>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(com.example.cookingsocialnetwork.R.layout.recyclerview_item_formimage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemImage = photosUri[position]
        holder.bindImage(itemImage)
    }

    override fun getItemCount(): Int {
       return photosUri.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //2
        private var view: View = itemView;
        private var itemImage : ImageView

//        //3
        init {
            //v.setOnClickListener(this)
            itemImage = itemView.findViewById(com.example.cookingsocialnetwork.R.id.item_ImageChoose)

        }

        fun bindImage(uri: Uri){
            this.itemImage.setImageURI(uri)
        }
//        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
//        companion object {
//            //5
//            private val PHOTO_KEY = "PHOTO"
//        }
    }
}