package com.example.cookingsocialnetwork.main.fragment.search.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.cookingsocialnetwork.R

class ListAdapterSearchHistory(private var dialog: Dialog, private var history: MutableList<String>):
    ArrayAdapter<String>(dialog.context, R.layout.list_search_history_item, history) {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_search_history_item, null)

        val textSearch: TextView = view.findViewById(R.id.search_text)
        val searchBtn: Button = view.findViewById(R.id.search_btn)

        textSearch.text = history[position]

        searchBtn.setOnClickListener()
        {
            val searchView = dialog.findViewById<View>(R.id.search) as androidx.appcompat.widget.SearchView
            searchView.setQuery(history[position], false)
        }

        return view
    }
}