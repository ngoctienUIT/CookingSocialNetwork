package com.example.cookingsocialnetwork.post2.model

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import java.util.*

class StepAdapter(private val stepList: MutableList<Step>): RecyclerView.Adapter<StepAdapter.ViewHolder>(), StepRowMoveCallBack.RecyclerViewRowTouchHelperContract {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_page_2_step_child, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = stepList[position]
        holder.stepCount.text = "Bước " + (position + 1).toString()
        //holder.stepImage.load(currentItem.image)
        holder.step.text = currentItem.step

        holder.delete.setOnClickListener {
            stepList.remove(currentItem)
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount() = stepList.size

    class ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView) {
        val stepCount: TextView = itemView.findViewById(R.id.post_page2_step_count)
        val stepImage: ImageView = itemView.findViewById(R.id.post_page2_step_image)
        val step: TextView = itemView.findViewById(R.id.post_page2_step_text)
        val delete: ImageView = itemView.findViewById(R.id.post_page_2_step_child_delete_btn)
    }

    override fun onRowMove(from: Int, to: Int) {
        Collections.swap(stepList, from, to)
        notifyItemMoved(from, to)
    }

    override fun onRowSelected(stepViewHolder: ViewHolder?) {
        stepViewHolder?.itemView?.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(stepViewHolder: ViewHolder?) {
        stepViewHolder?.itemView?.setBackgroundColor(Color.WHITE)
    }
}