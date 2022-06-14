package com.example.cookingsocialnetwork.viewpost.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.post2.model.Step
import com.example.cookingsocialnetwork.post2.model.StepFireBase
import java.lang.ref.WeakReference

class MethodsAdapter(private var methods: MutableList<StepFireBase>) :
    RecyclerView.Adapter<MethodsAdapter.MethodsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_methods, parent, false)
        return MethodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MethodsViewHolder, position: Int) {
      //  holder.method = methods[position]
        holder.number = position + 1
        val step = methods[position]
        holder.updateView(step)
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

        fun updateView(step: StepFireBase) {
            findView()
            order?.text = number.toString()
            content?.text = step.step
            image?.load(step.image)
        }
    }
}