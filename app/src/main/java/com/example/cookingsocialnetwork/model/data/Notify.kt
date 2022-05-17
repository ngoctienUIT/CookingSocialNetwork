package com.example.cookingsocialnetwork.model.data

import java.text.SimpleDateFormat
import java.util.*

data class Notify(var name: String, var id: String, var type: String, var status: Long, var time: String) {
    constructor(): this("","","",1,"")

    var content: String = ""

    fun getData(data: Map<String, Any>)
    {
        name = data["name"] as String
        type = data["type"] as String
        status = data["status"] as Long
        if (type.compareTo("comment") == 0) content = data["content"] as String
        val timestamp = data["time"] as com.google.firebase.Timestamp
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("hh:mm:ss - dd/MM/yyyy")
        val netDate = Date(milliseconds)
        time = sdf.format(netDate).toString()
    }
}