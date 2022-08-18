package com.example.i_go.feature_note.presentation.doctors.hospitals

import com.example.i_go.feature_note.data.remote.responseDTO.HospitalDTO

data class HospitalState(
    val isLoading: Boolean = false,
    val hospitalDTOs: List<HospitalDTO> = emptyList(),
    val error: String = ""
)
