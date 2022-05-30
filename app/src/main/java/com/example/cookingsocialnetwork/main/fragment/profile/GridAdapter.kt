package com.example.cookingsocialnetwork.main.fragment.profile

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cookingsocialnetwork.R

class GridAdapter(var context: Context, var postArrayList: ArrayList<PostItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return postArrayList.size
    }

    override fun getItem(p0: Int): Any {
        return postArrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View = View.inflate(context, R.layout.grid_item,null)
        var picture: ImageView = view.findViewById(R.id.grid_item_image)
        var name: TextView = view.findViewById(R.id.grid_item_name)
        var timetomake: TextView = view.findViewById(R.id.grid_item_time_to_make)
        var description: TextView = view.findViewById(R.id.grid_item_description)
        var rating: TextView = view.findViewById(R.id.grid_item_rating)
        var listItem : PostItem = postArrayList[p0]
        picture.setImageResource(listItem.picture!!)
        name.text = listItem.name
        timetomake.text = listItem.timetomake
        description.text = listItem.timetomake
        rating.text = listItem.rating.toString()
        return view
    }
}