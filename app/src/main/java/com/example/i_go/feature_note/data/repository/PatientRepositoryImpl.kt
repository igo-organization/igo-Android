package com.example.i_go.feature_note.data.repository

import com.example.i_go.feature_note.data.remote.PatientAPI
import com.example.i_go.feature_note.data.remote.responseDTO.HospitalDetailDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientByIdDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientDeleteDTO
import com.example.i_go.feature_note.domain.repository.PatientRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepositoryImpl @Inject constructor(
    private val api: PatientAPI
) : PatientRepository {

    override suspend fun getPatients(doctor_id: Int): Response<List<PatientByIdDTO>> {
        return api.getPatients(doctor_id)
    }

    override suspend fun getPatientById(id: Int): PatientDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun insertPatient(patient: PatientDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePatient(
        doctor_id: Int,
        patient_id: Int
    ): Response<PatientDeleteDTO> {
        return api.deletePatient(doctor_id, patient_id)
    }
}