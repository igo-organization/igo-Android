package com.example.i_go.feature_note.domain.use_case.notification

import com.example.i_go.feature_note.domain.model.Notification
import com.example.i_go.feature_note.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotifications(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<List<Notification>> {
        return repository.getNotifications()
    }
}