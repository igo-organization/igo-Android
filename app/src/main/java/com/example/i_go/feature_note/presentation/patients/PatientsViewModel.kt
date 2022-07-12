package com.example.i_go.feature_note.presentation.patients

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.use_case.PatientUseCases
import com.example.i_go.feature_note.domain.util.OrderType
import com.example.i_go.feature_note.domain.util.PatientOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor (
    private val patientUseCases: PatientUseCases
) : ViewModel() {
    private val _state = mutableStateOf(PatientsState())
    val state: State<PatientsState> = _state

    private var recentlyDeletedPatient: Patient? = null

    private var getPatientsJob: Job? = null

    init {
        getPatients(PatientOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: PatientsEvent) {
        when (event) {
            is PatientsEvent.Order -> {
                if (state.value.patientOrder::class == event.patientOrder::class &&
                    state.value.patientOrder.orderType == event.patientOrder.orderType
                ) {
                    return
                }
                getPatients(event.patientOrder)
            }
            is PatientsEvent.DeletePatients -> {
                viewModelScope.launch {
                    patientUseCases.deletePatient(event.patient)
                    recentlyDeletedPatient = event.patient
                }
            }
            is PatientsEvent.RestorePatients -> {
                viewModelScope.launch {
                    patientUseCases.addPatient(recentlyDeletedPatient ?: return@launch)
                    recentlyDeletedPatient = null
                }
            }
            is PatientsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getPatients(patientOrder: PatientOrder) {
        getPatientsJob?.cancel()
        getPatientsJob = patientUseCases.getPatients(patientOrder)
            .onEach { patients ->
                _state.value = state.value.copy(
                    patients = patients,
                    patientOrder = patientOrder
                )
            }
            .launchIn(viewModelScope)
    }
}