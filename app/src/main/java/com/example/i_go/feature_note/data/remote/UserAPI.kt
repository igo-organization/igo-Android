package com.example.i_go.feature_note.data.remote

import com.example.i_go.feature_note.data.remote.requestDTO.LoginPasswordDTO
import com.example.i_go.feature_note.data.remote.requestDTO.SignInDTO
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.HospitalDTO
import com.example.i_go.feature_note.data.remote.responseDTO.SignInResponseDTO
import com.example.i_go.feature_note.domain.model.Token
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @POST("/users/login/")
    suspend fun login(
        @Body loginpasswordDTO : LoginPasswordDTO
    ): Response<Token>

    @POST("/users/register/")
    suspend fun signIn(
        @Body signInDTO : SignInDTO
    ): Response<SignInResponseDTO>

    @GET("/users/hospital/")
    suspend fun getHospitals(): Response<List<HospitalDTO>>

    @GET("/users/doctor/")
    suspend fun getUserInfo(): Response<UserDTO>
}