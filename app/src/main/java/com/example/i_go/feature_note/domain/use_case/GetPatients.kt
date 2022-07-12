package com.example.i_go.feature_note.domain.use_case

import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.repository.PatientRepository
import com.example.i_go.feature_note.domain.util.OrderType
import com.example.i_go.feature_note.domain.util.PatientOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPatients (
    private val repository: PatientRepository
){
    operator fun invoke(
        patientOrder: PatientOrder = PatientOrder.Date(OrderType.Descending)
    ): Flow<List<Patient>> {
        return repository.getPatients().map { patients ->
            when(patientOrder.orderType) {
                is OrderType.Ascending -> {
                    when(patientOrder) {
                        is PatientOrder.Name -> patients.sortedBy { it.name.lowercase() }
                        is PatientOrder.Date -> patients.sortedBy { it.timestamp }
                        is PatientOrder.Image -> patients.sortedBy { it.image }
                        is PatientOrder.Diseases -> patients.sortedBy { it.disease }
                    }
                }
                is OrderType.Descending -> {
                    when(patientOrder) {
                        is PatientOrder.Name -> patients.sortedByDescending { it.name.lowercase() }
                        is PatientOrder.Date -> patients.sortedByDescending { it.timestamp }
                        is PatientOrder.Image -> patients.sortedByDescending { it.image }
                        is PatientOrder.Diseases -> patients.sortedByDescending { it.disease }}
                }
            }
        }
    }
}