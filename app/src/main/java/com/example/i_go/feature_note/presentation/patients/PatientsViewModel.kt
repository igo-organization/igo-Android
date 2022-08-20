package com.example.i_go.feature_note.presentation.patients

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.data.remote.responseDTO.PatientDTO
import com.example.i_go.feature_note.domain.use_case.patient.GetPatients
import com.example.i_go.feature_note.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor (
    private val getPatientsUseCases: GetPatients
) : ViewModel() {

    private val _state = mutableStateOf(PatientsState())
    val state: State<PatientsState> = _state

    private var recentlyDeletedPatient: PatientDTO? = null

    /*
    init {
        getPatients(0)
    }
*/
    fun onEvent(event: PatientsEvent, users_id: Int) {
        when (event) {
            /*
            is PatientsEvent.DeletePatients -> {
                viewModelScope.launch {
                    getPatientsUseCases.deletePatient(event.patient)
                    recentlyDeletedPatient = event.patient
                }
            }
            is PatientsEvent.RestorePatients -> {
                viewModelScope.launch {
                    getPatientsUseCases.addPatient(recentlyDeletedPatient ?: return@launch)
                    recentlyDeletedPatient = null
                }
            }
            is PatientsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }*/
        }
    }

    fun getPatients(user_id: Int) {
        getPatientsUseCases(user_id).onEach { result ->
            when (result){
                is Resource.Success -> {
                    _state.value = result.data?.let { PatientsState(patientDTOS = it) }!!
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
        }.launchIn(viewModelScope)
    }
}