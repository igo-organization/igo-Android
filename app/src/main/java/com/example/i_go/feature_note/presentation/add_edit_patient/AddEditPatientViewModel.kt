package com.example.i_go.feature_note.presentation.add_edit_patient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.domain.model.InvalidNoteException
import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.use_case.PatientUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPatientViewModel @Inject constructor (
    private val patientUseCases: PatientUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _patientName = mutableStateOf(PatientTextFieldState(
        hint = "환자 이름을 입력해주세요.   ex) 홍길동"
    ))
    val patientName: State<PatientTextFieldState> = _patientName

    private val _patientSex = mutableStateOf(PatientTextFieldState(
        hint = "환자 성별을 입력해주세요.   ex) 남"
    ))
    val patientSex: State<PatientTextFieldState> = _patientSex

    private val _patientAge = mutableStateOf(PatientTextFieldState(
        hint = "나이을 입력해주세요.       ex) 70세"
    ))
    val patientAge: State<PatientTextFieldState> = _patientAge

    private val _patientBloodType= mutableStateOf(PatientTextFieldState(
        hint = "혈액형을 입력해주세요.     ex) Rh+ A형"
    ))
    val patientBloodType: State<PatientTextFieldState> = _patientBloodType

    private val _patientDiseases = mutableStateOf(PatientTextFieldState(
        hint = "환자의 질병 정보를 입력해주세요.   ex) 치매"
    ))
    val patientDiseases: State<PatientTextFieldState> = _patientDiseases

    private val _patientExtra = mutableStateOf(PatientTextFieldState(
        hint = "기타 사항을 입력해주세요. \nex) 어르신 거동이 불편하심"
    ))
    val patientExtra: State<PatientTextFieldState> = _patientExtra

    private val _patientImage = mutableStateOf(Patient.patientImages.random())
    val patientImage: State<Int> = _patientImage

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPatientId: Int? = null

    init {
        savedStateHandle.get<Int>("patientId")?.let { patientId ->
            if(patientId != -1) {
                viewModelScope.launch {
                    patientUseCases.getPatient(patientId)?.also { patient ->
                        currentPatientId = patient.id
                        _patientName.value = patientName.value.copy(
                            text = patient.name,
                            isHintVisible = false
                        )
                        _patientSex.value = _patientSex.value.copy(
                            text = patient.sex,
                            isHintVisible = false
                        )
                        _patientAge.value = _patientAge.value.copy(
                            text = patient.age,
                            isHintVisible = false
                        )
                        _patientBloodType.value = _patientBloodType.value.copy(
                            text = patient.blood_type,
                            isHintVisible = false
                        )
                        _patientDiseases.value = _patientDiseases.value.copy(
                            text = patient.disease,
                            isHintVisible = false
                        )
                        _patientExtra.value = _patientExtra.value.copy(
                            text = patient.extra,
                            isHintVisible = false
                        )

                        _patientImage.value = patient.image
                    }
                }
            }
        }
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
            is AddEditPatientEvent.ChangeSexFocus -> {
                _patientSex.value = _patientSex.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _patientSex.value.text.isBlank()
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
            is AddEditPatientEvent.ChangeBloodTypeFocus -> {
                _patientBloodType.value = _patientBloodType.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _patientBloodType.value.text.isBlank()
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
                        patientUseCases.addPatient(
                            Patient(
                                name = patientName.value.text,
                                sex = patientSex.value.text,
                                age = patientAge.value.text,
                                blood_type = patientBloodType.value.text,
                                disease = patientDiseases.value.text,
                                extra = patientExtra.value.text,

                                timestamp = System.currentTimeMillis(),
                                image = patientImage.value,
                                id = currentPatientId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch(e: InvalidNoteException) {
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