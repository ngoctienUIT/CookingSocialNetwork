package com.example.cookingsocialnetwork.model.service

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SendNotify {
    companion object {
        var token : String? = null

        private const val key : String = "AAAAa7W58TM:APA91bEcxf_QQvcGy5-CWHk8QzBHSgQZ9Z-I6RztGDcFfCMd-_nW5s51Ba3qt1wL5oG-Whn8IJ0fQNQy0R8etM_eocV9_lv-EB1_mTjOsxpCnXt8BxwjfK6vu8wb3_MKgUbuOY4HFDuI"

        fun subscribeTopic(context: Context, topic: String) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
                Toast.makeText(context, "Subscribed $topic", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to Subscribe $topic", Toast.LENGTH_LONG).show()
            }
        }

        fun unsubscribeTopic(context: Context, topic: String) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
                Toast.makeText(context, "Unsubscribed $topic", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to Unsubscribe $topic", Toast.LENGTH_LONG).show()
            }
        }

        fun logRegToken() {
            Firebase.messaging.token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result

                // Log
                val msg = "FCM Registration token: $token"
                Log.d(ContentValues.TAG, msg)
            }
        }

        fun sendMessage(content: String, username: String, receiver: String, id: String, type: String, topic: String) {
            GlobalScope.launch {
                val endpoint = "https://fcm.googleapis.com/fcm/send"
                try {
                    val url = URL(endpoint)
                    val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                    httpsURLConnection.readTimeout = 10000
                    httpsURLConnection.connectTimeout = 15000
                    httpsURLConnection.requestMethod = "POST"
                    httpsURLConnection.doInput = true
                    httpsURLConnection.doOutput = true

                    // Adding the necessary headers
                    httpsURLConnection.setRequestProperty("authorization", "key=$key")
                    httpsURLConnection.setRequestProperty("Content-Type", "application/json")

                    // Creating the JSON with post params
                    val body = JSONObject()

                    val data = JSONObject()
                    data.put("content", content)
                    data.put("name", username)
                    data.put("receiver", receiver)
                    data.put("id", id)
                    data.put("type", type)
                    body.put("data",data)

                    body.put("to","/topics/$topic")

                    val outputStream: OutputStream = BufferedOutputStream(httpsURLConnection.outputStream)
                    val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))
                    writer.write(body.toString())
                    writer.flush()
                    writer.close()
                    outputStream.close()
                    val responseCode: Int = httpsURLConnection.responseCode
                    val responseMessage: String = httpsURLConnection.responseMessage
                    Log.d("Response:", "$responseCode $responseMessage")
                    var result = String()
                    var inputStream: InputStream? = null
                    inputStream = if (responseCode in 400..499) {
                        httpsURLConnection.errorStream
                    } else {
                        httpsURLConnection.inputStream
                    }

                    if (responseCode == 200) {
                        Log.e("Success:", "notification sent $username \n $content")
                        // The details of the user can be obtained from the result variable in JSON format
                    } else {
                        Log.e("Error", "Error Response")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}