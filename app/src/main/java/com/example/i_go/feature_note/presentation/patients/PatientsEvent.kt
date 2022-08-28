package com.example.i_go.feature_note.presentation.patients


sealed class PatientsEvent {
    object DeletePatient: PatientsEvent()
    data class EnteredText(val message: String): PatientsEvent()
    object CallPatient: PatientsEvent()
    object RestorePatients: PatientsEvent()
    object ToggleOrderSection: PatientsEvent()
}