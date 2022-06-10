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
        val notifyTime = data["time"] as HashMap<String, Any>
        time.getTime(notifyTime)
    }

    fun  getDataNotify(data: Map<String, Any>)
    {
        name = data["name"] as String
        type = data["type"] as String
        id = data["id"] as String
        content = data["content"] as String
    }

    fun compareTo(notify: Notify): Boolean
    {
        return (notify.name.compareTo(name) == 0 && notify.type.compareTo(type) == 0
                && notify.content.compareTo(content) == 0 && notify.id.compareTo(id) == 0)
    }
}