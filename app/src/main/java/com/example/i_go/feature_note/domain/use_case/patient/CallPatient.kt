package com.example.i_go.feature_note.domain.use_case.patient

import com.example.i_go.feature_note.data.remote.responseDTO.PatientMessageDTO
import com.example.i_go.feature_note.domain.repository.PatientRepository
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CallPatient @Inject constructor(
    private val repository: PatientRepository
) {
    operator fun invoke(doctor_id: Int, patient_id: Int, messageDTO: PatientMessageDTO): Flow<Resource<PatientMessageDTO>> = flow {
        try {
            emit(Resource.Loading())
            val r = repository.callPatient(doctor_id, patient_id, messageDTO)
            when(r.code()) {
                200 -> {
                    "this is new".log()
                    emit(Resource.Success(r.body()!!))
                }
                else -> r.errorBody().toString().log()
            }
        } catch(e: HttpException) {
            "An unexpected error occured".log()
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            "Couldn't reach server. Check your internet connection".log()
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}