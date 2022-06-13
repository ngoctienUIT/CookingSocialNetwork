package com.example.cookingsocialnetwork.model.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.main.MainPage
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.newviewpost.ViewPost
import com.example.cookingsocialnetwork.viewpost.ViewFullPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Picasso
import java.util.*

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.w("MyFirebaseMsgService", "New Token: $token")
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        createNotificationChannel()
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("MyFirebaseMsgService", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("MyFirebaseMsgService", "Message data payload: ${remoteMessage.data}")
            val receiver = remoteMessage.data["receiver"] as String
            if (receiver == FirebaseAuth.getInstance().currentUser?.email.toString()) {
                val notify = Notify()
                notify.getDataNotify(remoteMessage.data)
                initNotify(notify)
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("MyFirebaseMsgService", "Message Notification Body: ${it.body}")
        }
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
                showNotify(title, content, item, avatar)
            }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.P)
    private fun showNotify(title: String, content: String, item: Notify, url: String) {
        val intent: Intent
        if (item.type == "follow") {
            intent = Intent(this, MainPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        else
        {
            intent = Intent(this, ViewPost::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("id_post", item.id)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val context = this
        Picasso.get().load(url).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val mBuilder =
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
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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