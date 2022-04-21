package com.example.cookingsocialnetwork.post.chooseImage
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecyclerAdapter(private val mphotosUriLiveData: MutableLiveData<MutableList<Uri>>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val view = LayoutInflater.from(parent.context).inflate(com.example.cookingsocialnetwork.R.layout.recyclerview_item_formimage, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val itemImage = mphotosUriLiveData.value?.get(position)!!
            holder.bindImage(itemImage)

    }

    override fun getItemCount(): Int {
       return mphotosUriLiveData.value?.size!!
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //2
        private var view: View = itemView;
        private var itemImage : ImageView
        private lateinit var itemTextImageView : TextView


//        //3
        init {
            //v.setOnClickListener(this)
            itemImage = itemView.findViewById(com.example.cookingsocialnetwork.R.id.item_ImageChoose)

        }

        fun bindImage(uri: Uri){
            //.itemImage.setImageURI(uri)
            Picasso.get().load(uri).into(this.itemImage)
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