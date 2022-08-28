package com.example.i_go.feature_note.presentation.add_edit_patient

import com.example.i_go.feature_note.data.remote.responseDTO.PatientByIdDTO
import com.example.i_go.feature_note.data.remote.responseDTO.UserResponseDTO

data class PatientState (
    val isLoading: Boolean = false,
    var patientByIdDTO: PatientByIdDTO = PatientByIdDTO(),
    val error: String = "",
)