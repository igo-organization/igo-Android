package com.example.i_go.feature_note.data.remote

import com.example.i_go.feature_note.data.remote.responseDTO.PatientByIdDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface PatientAPI {

    // 6 전체 환자 조회
    @GET("users/doctor/{doctor_id}/patient/")
    suspend fun getPatients(
        @Path ("doctor_id") doctor_id: Int
    ): Response<List<PatientByIdDTO>>

    //7 환자 생성
    @POST("users/doctor/{doctor_id}/patient/")
    suspend fun postPatient(
        @Path ("doctor_id") doctor_id: Int,
        @Body patientDTO: PatientDTO
    ): Response<PatientDTO>

    // 8 환자 한 명 조회
    @GET("users/doctor/{doctor_id}/patient/{patient_id}/")
    suspend fun getPatientById(
        @Path ("doctor_id") doctor_id: Int,
        @Path ("patient_id") patient_id: Int,
    ): Response<PatientByIdDTO>

    // 9 환자 수정
    @PUT("users/doctor/{doctor_id}/patient/{patient_id}/")
    suspend fun putPatient(
        @Path ("doctor_id") doctor_id: Int,
        @Path ("patient_id") patient_id: Int,
        @Body patientDTO: PatientDTO
    ): Response<PatientDTO>

    // 10 환자 삭제
    @DELETE("users/doctor/{doctor_id}/patient/{patient_id}/")
    suspend fun deletePatient(
        @Path ("doctor_id") doctor_id: Int,
        @Path ("patient_id") patient_id: Int
    ): Response<NullPointerException>
}