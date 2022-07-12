package com.example.i_go.feature_note.domain.repository

import com.example.i_go.feature_note.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    fun getPatients(): Flow<List<Patient>>

    suspend fun getPatientById(id: Int): Patient?

    suspend fun insertPatient(note: Patient)

    suspend fun deletePatient(note: Patient)
}