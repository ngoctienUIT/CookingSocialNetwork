package com.example.cookingsocialnetwork.viewpost.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cookingsocialnetwork.R

class ListIngredientAdapter(context: Activity, private var listIngredient: MutableList<String>):
    ArrayAdapter<String>(context, R.layout.item_ingredient , listIngredient) {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_ingredient, null)

        val nameIngredient: TextView = view.findViewById(R.id.name_ingredient)
        val amount: TextView = view.findViewById(R.id.amount)

        nameIngredient.text = listIngredient[position]

        return view
    }
}