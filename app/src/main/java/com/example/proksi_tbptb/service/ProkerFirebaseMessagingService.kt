package com.example.proksi_tbptb.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.example.proksi_tbptb.MainActivity
import com.example.proksi_tbptb.R
import com.example.proksi_tbptb.data.local.UserPreferences
import com.example.proksi_tbptb.data.remote.retrofit.ApiConfig
import com.example.proksi_tbptb.data.remote.retrofit.ApiService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProkerFirebaseMessagingService : FirebaseMessagingService() {
    private val CHANNEL_ID = "detail_proker_channel"
    private val userPreferences = UserPreferences()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        Log.d("Firebase", "New token generated: $token")
        GlobalScope.launch {
            try {
                userPreferences.saveFcmToken(applicationContext, token)
                Log.d("Firebase", "FCM Token saved to DataStore: $token")
                sendTokenToServer(token)
            } catch (e: Exception) {
                Log.e("Firebase", "Error saving FCM token", e)
            }
        }
    }

    private fun sendTokenToServer(token: String) {
        val apiService = ApiConfig.api
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Firebase", "Sending token to server: $token")
                val response = apiService.registerToken(ApiService.TokenRequest(token))
                if (response.isSuccessful) {
                    Log.d("Firebase", "Token registered successfully")
                }
            } catch (e: Exception) {
                Log.e("Firebase", "Failed to register token", e)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Firebase", "Message received from: ${remoteMessage.from}")
        Log.d("Firebase", "Data: ${remoteMessage.data}")
        Log.d("Firebase", "Notification: ${remoteMessage.notification}")
        remoteMessage.data.let { data ->
            val title = data["title"] ?: remoteMessage.notification?.title ?: "Detail Proker Baru"
            val body = data["body"] ?: remoteMessage.notification?.body ?: "Ada pembaruan detail proker"
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigation_destination", "all-proker")
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // Menambah prioritas agar terlihat jelas

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Detail Proker Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                lightColor = Color.Blue.toArgb()
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}