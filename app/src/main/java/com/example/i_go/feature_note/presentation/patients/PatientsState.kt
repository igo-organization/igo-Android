package com.example.i_go.feature_note.presentation.patients

import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.util.OrderType
import com.example.i_go.feature_note.domain.util.PatientOrder

data class PatientsState (
    val patients: List<Patient> = emptyList(),
    val patientOrder: PatientOrder = PatientOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)