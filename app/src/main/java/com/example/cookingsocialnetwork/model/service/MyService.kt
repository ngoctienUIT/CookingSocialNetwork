package com.example.cookingsocialnetwork.model.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.main.MainPage
import com.example.cookingsocialnetwork.model.data.Notify
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.squareup.picasso.Picasso
import java.util.*

class MyService: Service() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        getNotify()
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NotificationCompat.EXTRA_NOTIFICATION_ID)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(1102, notification)
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getNotify() {
        var notifyList: MutableList<Notify>
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .addSnapshotListener()
            { snapshot, e ->
                if (e != null) return@addSnapshotListener

                if (snapshot != null && snapshot.exists()) {
                    notifyList = mutableListOf()
                    val data = snapshot.data
                    val notifyData = data?.get("notify") as MutableList<Map<String, Any>>
                    Log.w("error", notifyData.toString())
                    for (item in notifyData) {
                        val notify = Notify()
                        notify.getData(item)
                        notifyList.add(notify)
                    }
                    checkNotify(notifyList)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkNotify(notifyList: MutableList<Notify>) {
        val updateList: MutableList<Map<String, Any>> = mutableListOf()
        for (item in notifyList)
            if (item.status == 1.toLong()) {
                initNotify(item)
                item.status = 0
                updateList.add(item.convertToMap())
            } else updateList.add(item.convertToMap())

        updateNotify(updateList)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun initNotify(item: Notify) {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(item.name)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                val name = info["name"].toString()
                val avatar = info["avatar"].toString()
                val title: String
                val content: String
                when (item.type) {
                    "comment" -> {
                        title = getString(R.string.comment)
                        content = "$name đã bình luận: ${item.content}"
                    }
                    "favorite" -> {
                        title = getString(R.string.favorite)
                        content = "$name đã thích bài viết"
                    }
                    else -> {
                        title = getString(R.string.follow)
                        content = "$name đã theo dõi bạn"
                    }
                }
                showNotify(title, content, avatar)
            }
    }

    private fun updateNotify(updateList: MutableList<Map<String, Any>>) {
        firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .update("notify", updateList)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.P)
    private fun showNotify(title: String, content: String, url: String) {
        val intent = Intent(this, MainPage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,0)
        val context = this
        Picasso.get().load(url).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val mBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(
                        context,
                        NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID
                    )
                        .setSmallIcon(R.drawable.ic_cooking)
                        .setLargeIcon(bitmap)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setAutoCancel(true)
                val random = Random()
                val m: Int = random.nextInt(9999 - 1000) + 1000
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(m, mBuilder.build())
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        })
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