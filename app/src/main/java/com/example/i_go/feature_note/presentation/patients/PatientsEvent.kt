package com.example.i_go.feature_note.presentation.patients

import com.example.i_go.feature_note.data.remote.responseDTO.PatientDTO

sealed class PatientsEvent {
    data class DeletePatients(val patient: PatientDTO): PatientsEvent()
    object RestorePatients: PatientsEvent()
    object ToggleOrderSection: PatientsEvent()
}