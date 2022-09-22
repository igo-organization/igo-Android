package com.example.i_go.feature_note.data.repository

import com.example.i_go.feature_note.data.notification_source.NotificationDao
import com.example.i_go.feature_note.domain.model.Notification
import com.example.i_go.feature_note.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(
    private val dao: NotificationDao
): NotificationRepository {

    override fun getNotifications(): Flow<List<Notification>> {
        return dao.getNotifications()
    }

    override suspend fun insertNotification(notification: Notification) {
        dao.insertNotification(notification)
    }
}