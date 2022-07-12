package com.example.i_go.feature_note.domain.use_case

import com.example.i_go.feature_note.domain.model.InvalidNoteException
import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.repository.PatientRepository

class AddPatient(
    private val repository: PatientRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(patient: Patient) {
        if(patient.name.isBlank()) {
            throw InvalidNoteException("The name is empty.")
        }
        if(patient.sex.isBlank()) {
            throw InvalidNoteException("The sex is empty.")
        }
        if(patient.age.isBlank()) {
            throw InvalidNoteException("The age is empty.")
        }
        if(patient.blood_type.isBlank()) {
            throw InvalidNoteException("The blood type is empty.")
        }
        if(patient.disease.isBlank()) {
            throw InvalidNoteException("The disease is empty.")
        }
        repository.insertPatient(patient)
    }
}

