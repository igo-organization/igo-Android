package com.example.i_go.feature_note.presentation.add_edit_patient

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.data.remote.responseDTO.PatientDTO
import com.example.i_go.feature_note.domain.use_case.patient.PatientUseCases
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPatientViewModel @Inject constructor (
    private val patientUseCases: PatientUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _patientName = mutableStateOf(PatientTextFieldState(
        hint = "홍길동"
    ))
    val patientName: State<PatientTextFieldState> = _patientName

    private val _patientSex = mutableStateOf(PatientRadioButtonState())
    val patientSex: State<PatientRadioButtonState> = _patientSex

    private val _patientAge = mutableStateOf(PatientTextFieldState(
        hint = "70세"
    ))
    val patientAge: State<PatientTextFieldState> = _patientAge

    private val _patientBloodType = mutableStateOf(PatientRadioButtonState())
    val patientBloodType: State<PatientRadioButtonState> = _patientBloodType

    private val _patientBloodRh = mutableStateOf(PatientRadioButtonState())
    val patientBloodRh: State<PatientRadioButtonState> = _patientBloodRh

    private val _patientDiseases = mutableStateOf(PatientTextFieldState(
        hint = "치매"
    ))
    val patientDiseases: State<PatientTextFieldState> = _patientDiseases

    private val _patientExtra = mutableStateOf(PatientTextFieldState(
        hint = "어르신 거동이 불편하심"
    ))
    val patientExtra: State<PatientTextFieldState> = _patientExtra

    private val _patientImage = mutableStateOf(PatientDTO.patientImages.random())
    val patientImage: State<Int> = _patientImage

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(PatientState())
    val state: State<PatientState> = _state

    var patientId = mutableStateOf(0)
    var doctorId = mutableStateOf(0)

    init {
        savedStateHandle.get<Int>("patientId")?.let { patient_id ->
            if(patient_id > 0) {
                viewModelScope.launch {
                    patientId.value = patient_id
                }
            }
        }
    }
    fun setDoctorId(doctor_id: Int){
        doctorId.value = doctor_id
    }
    fun getPatient(doctor_id: Int){
        patientUseCases.getPatientById(
            doctor_id,
            patientId.value
        ).onEach { patient ->
            when (patient) {
                is Resource.Success -> {
                    _patientName.value = patientName.value.copy(
                        text = patient.data?.name!!,
                        isHintVisible = false
                    )
                    _patientSex.value = _patientSex.value.copy(
                        bool_text = patient.data.gender!!
                    )
                    _patientAge.value = _patientAge.value.copy(
                        text = patient.data.age.toString(),
                        isHintVisible = false
                    )
                    _patientBloodType.value = _patientBloodType.value.copy(
                        int_text = patient.data.blood_type!!
                    )
                    _patientBloodRh.value = _patientBloodType.value.copy(
                        int_text = patient.data.blood_type
                    )
                    _patientDiseases.value = _patientDiseases.value.copy(
                        text = patient.data.disease!!,
                        isHintVisible = false
                    )
                    _patientExtra.value = _patientExtra.value.copy(
                        text = patient.data.extra!!,
                        isHintVisible = false
                    )
                    _patientImage.value = patient.data.image!!
                }
                is Resource.Error -> {
                    _state.value =
                        PatientState(error = patient.message ?: "An unexpected error occured")
                    Log.d("test", "error")
                }
                is Resource.Loading -> {
                    _state.value = PatientState(isLoading = true)
                    Log.d("test", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AddEditPatientEvent) {
        when(event) {
            is AddEditPatientEvent.EnteredName -> {
                _patientName.value = patientName.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.ChangeNameFocus -> {
                _patientName.value = patientName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            patientName.value.text.isBlank()
                )
            }
            is AddEditPatientEvent.EnteredSex -> {
                _patientSex.value = _patientSex.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.EnteredAge -> {
                _patientAge.value = _patientAge.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.ChangeAgeFocus -> {
                _patientAge.value = _patientAge.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _patientAge.value.text.isBlank()
                )
            }
            is AddEditPatientEvent.EnteredBloodType -> {
                _patientBloodType.value = _patientBloodType.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.EnteredBloodRh -> {
                _patientBloodRh.value = _patientBloodRh.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.EnteredDiseases -> {
                _patientDiseases.value = _patientDiseases.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.ChangeDiseasesFocus -> {
                _patientDiseases.value = _patientDiseases.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _patientDiseases.value.text.isBlank()
                )
            }
            is AddEditPatientEvent.EnteredExtra -> {
                _patientExtra.value = _patientExtra.value.copy(
                    text = event.value
                )
            }
            is AddEditPatientEvent.ChangeExtraFocus -> {
                _patientExtra.value = _patientExtra.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _patientExtra.value.text.isBlank()
                )
            }

            is AddEditPatientEvent.ChangeImage -> {
                _patientImage.value = event.image
            }
            is AddEditPatientEvent.SavePatient -> {
                viewModelScope.launch {
                    try {
                        if (patientId.value <= 0) {
                            "Hello".log()
                            doctorId.value.toString().log()
                            patientUseCases.addPatient(
                                doctorId.value,
                                PatientDTO(
                                    name = patientName.value.text,
                                    gender = patientSex.value.bool_text,
                                    age = patientAge.value.text.toInt(),
                                    blood_type = patientBloodType.value.int_text,
                                    blood_rh = patientBloodRh.value.bool_text,
                                    disease = patientDiseases.value.text,
                                    extra = patientExtra.value.text,
                                    image = patientImage.value,
                                )
                            ).collectLatest {
                                when (it){
                                    is Resource.Success -> {
                                        "HIHI".log()
                                        _eventFlow.emit(UiEvent.SaveNote)
                                    }
                                    is Resource.Error -> {
                                        "환자 정보 저장 중 에러 발생 1".log()
                                        _eventFlow.emit(UiEvent.ShowSnackbar("cannot save"))
                                    }
                                    is Resource.Loading -> {
                                        "환자 정보 값 가져오는 중...".log()
                                    }
                                }
                            }
                        }
                        else {
                            "THis is new thing".log()
                            patientUseCases.putPatient(
                                doctorId.value,
                                patientId.value,
                                PatientDTO(
                                    name = patientName.value.text,
                                    gender = patientSex.value.bool_text,
                                    age = patientAge.value.text.toInt(),
                                    blood_type = patientBloodType.value.int_text,
                                    blood_rh = patientBloodRh.value.bool_text,
                                    disease = patientDiseases.value.text,
                                    extra = patientExtra.value.text,
                                    image = patientImage.value + 1,
                                )
                            ).collectLatest {
                                when (it){
                                    is Resource.Success -> {
                                        "HIHI".log()
                                        _eventFlow.emit(UiEvent.SaveNote)
                                    }
                                    is Resource.Error -> {
                                        "환자 정보 저장 중 에러 발생 1".log()
                                        _eventFlow.emit(UiEvent.ShowSnackbar("cannot save"))
                                    }
                                    is Resource.Loading -> {
                                        "환자 정보 값 가져오는 중...".log()
                                    }
                                }
                            }
                        }
                    } catch(e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "환자 정보를 저장할 수 없습니다."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}