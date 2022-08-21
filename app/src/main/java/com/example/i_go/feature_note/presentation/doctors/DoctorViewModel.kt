package com.example.i_go.feature_note.presentation.doctors

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.domain.use_case.user.PutUserInfo
import com.example.i_go.feature_note.domain.use_case.user.UserUseCases
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val userUseCases: PutUserInfo,
) : ViewModel() {

   // private val _state = mutableStateOf(DoctorState())
   // val state: State<DoctorState> = _state

    private val _doctor = mutableStateOf(UserDTO())
    val doctor: State<UserDTO> = _doctor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val message = ""

    private var _hospitalId = mutableStateOf(0)
    val hospitalId: State<Int> = _hospitalId
    /*
    init {
        getUserInfo(0)
    }*/

    fun onEvent(event: DoctorEvent, doctor_id: Int) {
        when (event) {
            is DoctorEvent.EnteredName -> {
                _doctor.value = doctor.value.copy(
                    name = event.value
                )
            }
            is DoctorEvent.EnteredMajor -> {
                _doctor.value = doctor.value.copy(
                    subjects = event.value
                )
            }
            is DoctorEvent.EnteredHospital -> {
                _doctor.value = doctor.value.copy(
                    hospital = 1
                )
            }
            is DoctorEvent.SaveDoctor -> {
                viewModelScope.launch {
                  //  try {
                        if (doctor.value.name.isBlank()
                            || doctor.value.hospital <= 0
                            || doctor.value.subjects.isBlank()
                        ) {
                            _eventFlow.emit(UiEvent.ShowSnackbar("모든 칸의 내용을 채워주세요"))
                            "ERROR 입력 되지 않은 칸이 존재".log()
                            return@launch
                        }
                        "우히히 doctor id = ${doctor_id}, ${doctor.value}".log()
                        userUseCases(
                            doctor_id = doctor_id,
                            userDTO = UserDTO(
                                name = doctor.value.name,
                                subjects = doctor.value.subjects,
                                hospital = 1//doctor.value.hospital
                            )
                        ).collect() {
                            when (it) {
                                is Resource.Success -> {
                                    "안뇽".log()
                                //    it.data!!.hospital.also { _hospitalId.value = it }
                                    _eventFlow.emit(UiEvent.SaveDoctor)
                                    "의료진 정보 올리기 성공 reviewId: ${it.data!!.hospital}".log()
                                }
                                is Resource.Error -> {
                                    "의료진 정보 올리기 실패".log()
                                    _eventFlow.emit(UiEvent.ShowSnackbar("실패!"))
                                }
                                is Resource.Loading -> "의료진 정보 올리는 중".log()

                            }
                        }
                   /* } catch (e: Exception) {
                        "의료진 저장 중 에러 발생".log()
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = "의료진 정보 저장 중 에러 발생"))
                    }*/
                }
            }
        }

    }

/*
    fun getUserInfo(userId: Int) {
        userUseCases.getUserInfo(userId).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = it.data?.let { DoctorState(userResponseDTO = it) }!!
                    "안녕: ${_state.value}".log()
                    "의료진 정보 가져오기 성공".log()
                }
                is Resource.Error -> {
                    DoctorState(error = it.message ?: "예상치 못한 오류 발생")
                    "의료진 정보 가져오기 실패".log()
                }
                is Resource.Loading -> {
                    DoctorState(isLoading = true)
                    "의료진 정보 가져오는 중".log()
                }
            }
        }.launchIn(viewModelScope)
    }
*/
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDoctor : UiEvent()
    }
}



