package com.example.i_go.feature_note.presentation.patients

import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.util.PatientOrder

sealed class PatientsEvent {
    data class Order(val patientOrder: PatientOrder): PatientsEvent()
    data class DeletePatients(val patient: Patient): PatientsEvent()
    object RestorePatients: PatientsEvent()
    object ToggleOrderSection: PatientsEvent()
}