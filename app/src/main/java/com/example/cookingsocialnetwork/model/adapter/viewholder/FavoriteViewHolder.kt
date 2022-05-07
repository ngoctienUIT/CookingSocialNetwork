package com.example.cookingsocialnetwork.model.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import java.lang.ref.WeakReference

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var contentView: TextView? = null
    private var timeView: TextView? = null
    private var avatarView: ImageView? = null
    var content = ""
    var onClickItem : ((String)->Unit)? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        contentView = view.get()?.findViewById(R.id.content)
        timeView = view.get()?.findViewById(R.id.time)
        avatarView=view.get()?.findViewById(R.id.avatar)
    }

    private fun setListener()
    {
        view.get()?.setOnClickListener()
        {
            onClickItem?.let { it(content) }
        }
    }

    fun updateView()
    {
        nameView?.text = content
    }
}