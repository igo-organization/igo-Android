package com.example.i_go.feature_note.firebase

import android.util.Log
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.i_go.R
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

        if (message.data.isNotEmpty()){
            Log.v("CloudMessage", "Message Data ${message.data}")
        }
        message.data?.let{
            Log.v("CloudMessage", "Message Notification Body ${it["body"]}")
        }

        if (message.notification != null){
            Log.v("CloudMessage", "Notification ${message.notification}")
            Log.v("CloudMessage", "Notification Title ${message.notification!!.title}")
            Log.v("CloudMessage", "Notification Body ${message.notification!!.body}")

        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                "Fetching FCM registration token failed ${task.exception}".log()
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
        }
        )
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        token.log()
        GlobalScope.launch{
            saveGCMToken(token)
        }
    }

    private suspend fun saveGCMToken(token: String) {
        val gckTokenKey = stringPreferencesKey("gcm_token")
        baseContext.dataStore.edit { pref ->
            pref[gckTokenKey] = token
        }
    }
}