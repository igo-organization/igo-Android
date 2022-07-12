package com.example.i_go.feature_note.data.repository

import com.example.i_go.feature_note.data.data_source.PatientDao
import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow

class PatientRepositoryImpl(
    private val dao: PatientDao
) : PatientRepository {
    override fun getPatients(): Flow<List<Patient>> {
        return dao.getPatients()
    }

    override suspend fun getPatientById(id: Int): Patient? {
        return dao.getPatientById(id)
    }

    override suspend fun insertPatient(patient: Patient) {
        dao.insertPatient(patient)
    }

    override suspend fun deletePatient(patient: Patient) {
        dao.deletePatient(patient)
    }

}