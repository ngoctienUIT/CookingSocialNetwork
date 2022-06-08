package com.example.cookingsocialnetwork.model.data

data class Notify(var name: String, var id: String, var type: String, var status: Long, var time: Time) {
    constructor() : this("", "", "", 1, Time())

    var content: String = ""
    private lateinit var currentTime: com.google.firebase.Timestamp

    fun getData(data: Map<String, Any>) {
        name = data["name"] as String
        type = data["type"] as String
        id = data["id"] as String
        content = data["content"] as String
//        val timestamp = data["time"] as com.google.firebase.Timestamp
//        currentTime = timestamp
//        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
//        val sdf = SimpleDateFormat("hh:mm:ss - dd/MM/yyyy")
//        val netDate = Date(milliseconds)
//        time = sdf.format(netDate).toString()
    }

    fun convertToMap(): Map<String, Any> {
        return hashMapOf(
            "name" to name,
            "type" to type,
            "status" to status,
            "id" to id,
            "content" to content,
            "time" to currentTime
        )
    }
}