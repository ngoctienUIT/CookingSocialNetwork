package com.example.cookingsocialnetwork.post2.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R

class IngredientAdapter(private val ingredientList: List<Ingredient>): RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_page_2_ingredient_child, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = ingredientList[position]

        holder.ingredientAmount.text = currentItem.amount
        holder.ingredientUnit.text = currentItem.unit
        holder.ingredientName.text = currentItem.name
    }

    override fun getItemCount() = ingredientList.size

    class ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView) {
        val ingredientAmount: TextView = itemView.findViewById(R.id.post_page2_ingredient_child_ingredient_amount)
        val ingredientUnit: TextView = itemView.findViewById(R.id.post_page2_ingredient_child_ingredient_unit)
        val ingredientName:  TextView = itemView.findViewById(R.id.post_page2_ingredient_child_ingredient_name)
    }

}