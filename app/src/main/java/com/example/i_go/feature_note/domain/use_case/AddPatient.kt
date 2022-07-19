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
            throw InvalidNoteException("이름을 입력해주세요.")
        }
        if(patient.sex.isBlank()) {
            throw InvalidNoteException("성별을 입력해주세요.")
        }
        if(patient.age.isBlank()) {
            throw InvalidNoteException("나이을 입력해주세요.")
        }
        if(patient.blood_type.isBlank()) {
            throw InvalidNoteException("혈액형을 입력해주세요.")
        }
        if(patient.disease.isBlank()) {
            throw InvalidNoteException("질병 정보를 입력해주세요.")
        }
        repository.insertPatient(patient)
    }
}

