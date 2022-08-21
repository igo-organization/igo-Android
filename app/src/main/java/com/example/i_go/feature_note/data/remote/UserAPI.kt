package com.example.i_go.feature_note.data.remote

import com.example.i_go.feature_note.data.remote.requestDTO.LoginPasswordDTO
import com.example.i_go.feature_note.data.remote.requestDTO.SignInDTO
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.HospitalDTO
import com.example.i_go.feature_note.data.remote.responseDTO.HospitalDetailDTO
import com.example.i_go.feature_note.data.remote.responseDTO.SignInResponseDTO
import com.example.i_go.feature_note.data.remote.responseDTO.UserResponseDTO
import com.example.i_go.feature_note.domain.model.Token
import retrofit2.Response
import retrofit2.http.*

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

    @GET("users/hospital/{hospital_id}/")
    suspend fun getHospital(
        @Path("hospital_id") hospital_id: Int,
    ): Response<HospitalDetailDTO>

    @GET("/users/doctor/{doctor_id}/")
    suspend fun getUserInfo(
        @Path("doctor_id") doctor_id: Int,
    ): Response<UserResponseDTO>

    @PUT("/users/doctor/{doctor_id}/")
    suspend fun putUserInfo(
        @Path("doctor_id") doctor_id: Int,
        @Body userDTO: UserDTO
    ): Response<UserDTO>
}