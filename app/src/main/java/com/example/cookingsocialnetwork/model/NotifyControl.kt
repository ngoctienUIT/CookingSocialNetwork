package com.example.cookingsocialnetwork.model

import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.Time
import com.example.cookingsocialnetwork.model.service.SendNotify
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

class NotifyControl {
    companion object {
        fun addNotify(userName: String, content: String, id: String, type: String)
        {
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(userName)
                .get()
                .addOnSuccessListener { snapshot ->
                    val data = snapshot.data
                    val notifyData = data?.get("notify") as MutableList<Map<String, Any>>

                    val notify = hashMapOf(
                        "content" to content,
                        "id" to id,
                        "name" to FirebaseAuth.getInstance().currentUser?.email.toString(),
                        "status" to 1,
                        "time" to LocalDateTime.now(),
                        "type" to type
                    )

                    SendNotify.sendMessage(
                        content,
                        FirebaseAuth.getInstance().currentUser?.email.toString(),
                        userName, id, type, "notification"
                    )

                    notifyData.add(notify)
                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(userName)
                        .update("notify", notifyData)
                }
        }

        fun removeNotify(userName: String, content: String, id: String, type: String)
        {
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(userName)
                .get()
                .addOnSuccessListener { snapshot ->
                    val data = snapshot.data
                    val notifyData = data?.get("notify") as MutableList<Map<String, Any>>
                    var count = 0
                    val myNotify = Notify(FirebaseAuth.getInstance().currentUser?.email.toString(), id, type, 1, Time())
                    myNotify.content = content
                    notifyData.forEach()
                    {
                        val notify = Notify()
                        notify.getData(it)
                        if (notify.compareTo(myNotify)) return@forEach
                        count++
                    }

                    if (count< notifyData.size) notifyData.removeAt(count)
                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(userName)
                        .update("notify", notifyData)
                }
        }
    }
}