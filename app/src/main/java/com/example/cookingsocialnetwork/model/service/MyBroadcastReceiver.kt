package com.example.cookingsocialnetwork.model.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            val intent = Intent(context, MyBroadcastReceiver::class.java)
            context.startForegroundService(intent)
        }
    }
}