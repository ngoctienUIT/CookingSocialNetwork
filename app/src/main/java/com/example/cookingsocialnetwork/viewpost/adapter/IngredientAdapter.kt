package com.example.cookingsocialnetwork.viewpost.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.post2.model.Ingredient
import java.lang.ref.WeakReference

class IngredientAdapter(private var ingredients: MutableList<Ingredient>)
    : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.updateView(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: WeakReference<View> = WeakReference(itemView)
        private var nameIngredient: TextView? = null
        private var amount: TextView? = null
        private var unit:TextView? = null
       // var ingredients: String? = null

        init {
            findView()
        }

        private fun findView() {
            nameIngredient = view.get()?.findViewById(R.id.name_ingredient)
            amount = view.get()?.findViewById(R.id.amount)
            unit = view.get()?.findViewById(R.id.unit)

        }

        fun updateView(ingredient: Ingredient) {
            findView()
            nameIngredient?.text = ingredient.name
            amount?.text = ingredient.amount
            unit?.text = ingredient.unit
        }
    }
}