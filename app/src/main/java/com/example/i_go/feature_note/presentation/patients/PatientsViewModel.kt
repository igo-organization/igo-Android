package com.example.i_go.feature_note.presentation.patients

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.data.remote.requestDTO.LoginPasswordDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientByIdDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientDTO
import com.example.i_go.feature_note.data.remote.responseDTO.PatientMessageDTO
import com.example.i_go.feature_note.domain.use_case.patient.GetPatients
import com.example.i_go.feature_note.domain.use_case.patient.PatientUseCases
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.add_edit_patient.AddEditPatientViewModel
import com.example.i_go.feature_note.presentation.login.LoginEvent
import com.example.i_go.feature_note.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor (
    private val patientUseCases: PatientUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PatientsState())
    val state: State<PatientsState> = _state

    private var recentlyDeletedPatient: PatientDTO? = null

    private var _message = mutableStateOf(PatientMessageDTO(message = ""))
    val message: State<PatientMessageDTO> = _message

    private val _eventFlow = MutableSharedFlow<AddEditPatientViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        getPatients(0)
    }

    fun onEvent(event: PatientsEvent, doctor_id: Int, patient_id: Int) {
        when (event) {
            is PatientsEvent.EnteredText -> {
                _message.value = message.value.copy(
                    message = event.message
                )
            }
            is PatientsEvent.DeletePatient -> {
                "?????? ?????? ????????? ??? ${event.patient}".log()
                recentlyDeletedPatient = event.patient
                patientUseCases.deletePatient(doctor_id, patient_id).launchIn(viewModelScope)

            }
            is PatientsEvent.CallPatient -> {
                patientUseCases.callPatient(patient_id).launchIn(viewModelScope)
                //     recentlyDeletedPatient = event.patient

            }

            is PatientsEvent.RestorePatients -> {
                "Restore ?????? ??????".log()
                viewModelScope.launch {
                    try {
                        addPatient(doctor_id = doctor_id, patientDTO = recentlyDeletedPatient!!)
                    } catch(e: Exception) {
                        _eventFlow.emit(
                            AddEditPatientViewModel.UiEvent.ShowSnackbar(
                                message = e.message ?: "?????? ????????? ????????? ??? ????????????."
                            )
                        )
                    }
                }
                recentlyDeletedPatient = null
            }
        }
    }

    private suspend fun addPatient(doctor_id: Int, patientDTO: PatientDTO){
        patientUseCases.addPatient(
            doctor_id = doctor_id,
            patientDTO = recentlyDeletedPatient!!
        ).collectLatest {
            when (it){
                is Resource.Success -> {
                    "HIHI".log()
                    _eventFlow.emit(AddEditPatientViewModel.UiEvent.SavePatient)
                }
                is Resource.Error -> {
                    "?????? ?????? ?????? ??? ?????? ?????? 1".log()
                    _eventFlow.emit(AddEditPatientViewModel.UiEvent.ShowSnackbar("cannot save"))
                }
                is Resource.Loading -> {
                    "?????? ?????? ??? ???????????? ???...".log()
                }
            }
        }
        recentlyDeletedPatient = null
    }
    fun getPatients(user_id: Int) {
        patientUseCases.getPatients(user_id).onEach { result ->
            when (result){
                is Resource.Success -> {
                    _state.value = result.data?.let { PatientsState(patientDTOS = it) }!!
                    "hi".log()
                    Log.d("test", "success : ${result.data}")
                }
                is Resource.Error -> {
                    _state.value = PatientsState(error = result.message ?: "An unexpected error occured")
                    Log.d("test", "error")
                }
                is Resource.Loading -> {
                    _state.value = PatientsState(isLoading = true)
                    Log.d("test", "loading")
                }
            }
           // delay(5000L)
        }.launchIn(viewModelScope)
    }

}