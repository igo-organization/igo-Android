package com.example.i_go.feature_note.presentation.util

sealed class Screen(val route: String) {
    object PatientsScreen: Screen("patients_screen")
    object AddEditPatientScreen: Screen("add_edit_patient_screen")
    object DoctorScreen: Screen("doctor_screen")
}
