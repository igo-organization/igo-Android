package com.example.i_go.feature_note.firebase

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.i_go.MainActivity
import com.example.i_go.feature_note.data.storage.dataStore
import com.example.i_go.feature_note.domain.util.log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TechFirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this)

        "Tech 진입".log()
        Log.v("CloudMessage", "From ${message.from}")
        "this is cloudsMessage ${message.data}".log()
        "value is ${message.data.values}".log()
        if (message.data.isNotEmpty()){
            "Message Data ${message.data}".log()
        }
        message.data.let{
            "Message Notification Body ${it["body"]}".log()
        }

        if (message.notification != null) {

            val title = getSharedPreferences("title", Context.MODE_PRIVATE)
            var editor = title.edit()
            editor.putString("title",message.notification!!.title)
            editor.apply()

            val body = getSharedPreferences("body", Context.MODE_PRIVATE)
            editor = body.edit()
            editor.putString("body",message.notification!!.body)
            editor.apply()

            val image = getSharedPreferences("image", Context.MODE_PRIVATE)
            editor = image.edit()
            editor.putString("image",message.notification!!.imageUrl.toString())
            editor.apply()

            "This is Note: ${title.getString("title", "defaultTitle")}".log()
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                "Fetching FCM registration token failed ${task.exception}".log()
                return@OnCompleteListener
            }

            val token = task.result
            token.log()

        }
        )
    }


    override fun onNewToken(token: String) {
        token.log()
        GlobalScope.launch{
            saveGCMToken(token)
        }
    }

    private suspend fun saveGCMToken(token: String) {
        token.log()
        val gckTokenKey = stringPreferencesKey("gcm_token")
        baseContext.dataStore.edit { pref ->
            pref[gckTokenKey] = token
        }
    }
}
