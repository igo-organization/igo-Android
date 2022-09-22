package com.example.i_go.feature_note.presentation.alarms

import com.example.i_go.feature_note.domain.model.Notification

data class AlarmState(
    val notifications: List<Notification> = emptyList()
)