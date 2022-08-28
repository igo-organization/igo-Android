package com.example.i_go.feature_note.presentation.patients

import com.example.i_go.feature_note.data.remote.responseDTO.PatientByIdDTO

sealed class PatientsEvent {
    object DeletePatient: PatientsEvent()
    object RestorePatients: PatientsEvent()
    object ToggleOrderSection: PatientsEvent()
}