package com.example.i_go.feature_note.firebase

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.compose.rememberNavController
import com.example.i_go.MainActivity
import com.example.i_go.feature_note.data.storage.dataStore
import com.example.i_go.feature_note.domain.util.log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TechFirebaseMessageService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.v("CloudMessage", "From ${message.from}")
        "this is cloudsMessage ${message.data}".log()

        if (message.data.isNotEmpty()){
            "Message Data ${message.data}".log()
        }
        message.data.let{
            "Message Notification Body ${it["body"]}".log()
        }

        if (message.notification != null) {
            "Notification ${message.notification}".log()
            "Notification Title ${message.notification!!.title}".log()
            "Notification Body ${message.notification!!.body}".log()
            "Notification Body ${message.notification!!.imageUrl}".log()
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                "Fetching FCM registration token failed ${task.exception}".log()
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            token.log()

        }
        )
        // 알림 오면 화면 깨우기
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        @SuppressLint("InvalidWakeLockTag") val wakeLock = pm.newWakeLock(
            PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "TAG"
        )
        wakeLock.acquire(3000)
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
