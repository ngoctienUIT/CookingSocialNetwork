package com.example.cookingsocialnetwork.model.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


class MyService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        var notifyList: MutableList<Notify>
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    notifyList = mutableListOf()
                    val data = snapshot.data
                    val notifyData = data?.get("notify") as MutableList<Map<String, Any>>

                    for (item in notifyData) {
                        val notify = Notify()
                        notify.getData(item)
                        notifyList.add(notify)
                    }

                    for (item in notifyList)
                        if (item.status == 1.toLong()) {
                            val mBuilder: NotificationCompat.Builder =
                                NotificationCompat.Builder(
                                    this,
                                    NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID
                                )
                                    .setSmallIcon(R.drawable.ic_cooking)
                                    .setContentTitle("My notification")
                                    .setContentText("Much longer text that cannot fit one line...")
                                    .setStyle(
                                        NotificationCompat.BigTextStyle()
                                            .bigText("Much longer text that cannot fit one line...")
                                    )
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            val notificationManager = NotificationManagerCompat.from(this)
                            notificationManager.notify(123, mBuilder.build())
                        }
                }
            }
        val notify = Notification()
        startForeground(123, notify)
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val name: CharSequence = getString(R.string.app_name)
        val description = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationChannel(
                NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID,
                name,
                importance
            )
        } else {
            TODO("VERSION.SDK_INT < P")
        }
        channel.description = description
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }
}