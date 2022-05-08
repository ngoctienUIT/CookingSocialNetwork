package com.example.cookingsocialnetwork.model.data

import java.time.LocalDateTime

data class Notify(var name: String, var type: String, var status: Long, var time: LocalDateTime) {
    fun getData(data: Map<String, Any>)
    {
        name = data["name"] as String
        type = data["type"] as String
    }
}