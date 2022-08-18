package com.example.i_go.feature_note.domain.repository

import com.example.i_go.feature_note.data.remote.requestDTO.LoginPasswordDTO
import com.example.i_go.feature_note.data.remote.requestDTO.SignInDTO
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.HospitalDTO
import com.example.i_go.feature_note.data.remote.responseDTO.SignInResponseDTO
import com.example.i_go.feature_note.domain.model.Token
import retrofit2.Response

interface UserRepository {
    suspend fun doLogin(emailPw: LoginPasswordDTO): Response<Token>
    suspend fun doSignIn(signInDTO: SignInDTO): Response<SignInResponseDTO>

    suspend fun getUserInfo(): Response<UserDTO>
    fun getUserToken(): Token
    fun setUserToken(token: Token)

    suspend fun getHospitals(): Response<List<HospitalDTO>>
}