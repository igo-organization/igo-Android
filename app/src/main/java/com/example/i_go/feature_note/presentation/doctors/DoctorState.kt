package com.example.i_go.feature_note.presentation.doctors

import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.UserResponseDTO

data class DoctorState (
    val isLoading: Boolean = false,
    val userDTO: UserDTO = UserDTO(),
    val userResponseDTO: UserResponseDTO = UserResponseDTO(),
    val error: String = "",
)