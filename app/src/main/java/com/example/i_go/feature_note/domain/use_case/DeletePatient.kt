package com.example.i_go.feature_note.domain.use_case

import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.repository.PatientRepository

class DeletePatient (
    private val repository: PatientRepository
) {
    suspend operator fun invoke(patient: Patient) {
        repository.deletePatient(patient)
    }
}