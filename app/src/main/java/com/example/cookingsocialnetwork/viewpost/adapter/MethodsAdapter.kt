package com.example.cookingsocialnetwork.viewpost.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import java.lang.ref.WeakReference

class MethodsAdapter(private var methods: MutableList<String>) :
    RecyclerView.Adapter<MethodsAdapter.MethodsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_methods, parent, false)
        return MethodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MethodsViewHolder, position: Int) {
        holder.method = methods[position]
        holder.number = position + 1
        holder.updateView()
    }

    override fun getItemCount(): Int = methods.size

    class MethodsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: WeakReference<View> = WeakReference(itemView)
        private var order: TextView? = null
        private var content: TextView? = null
        private var image: ImageView? = null
        var method: String? = null
        var number: Int? = null

        init {
            findView()
        }

        private fun findView() {
            order = view.get()?.findViewById(R.id.order)
            content = view.get()?.findViewById(R.id.content)
            image = view.get()?.findViewById(R.id.image)
        }

        fun updateView() {
            findView()
            order?.text = number.toString()
            content?.text = method
        }
    }
}