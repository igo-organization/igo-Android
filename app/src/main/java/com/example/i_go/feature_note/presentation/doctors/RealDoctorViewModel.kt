package com.example.i_go.feature_note.presentation.doctors

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.UserResponseDTO
import com.example.i_go.feature_note.domain.model.ID
import com.example.i_go.feature_note.domain.use_case.user.UserUseCases
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealDoctorViewModel @Inject constructor(
    val userUseCases: UserUseCases
) : ViewModel() {

    private var _user = mutableStateOf(UserDTO()) // 주는 애
    var user: State<UserDTO> = _user

    private var userResponseDTO = mutableStateOf(UserResponseDTO()) //받는 애
    private var id = mutableStateOf(ID())

    private var _postId = mutableStateOf(0)
    val postId: State<Int> = _postId

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
        }
    }
    private suspend fun putUserInfo(user_id: Int){
        userUseCases.putUserInfo(userDTO = _user.value, doctor_id = user_id).collectLatest {
            when (it) {
                is Resource.Success -> {
                  //  user.value = it.data!!
             //       userUseCases.getUserInfo(user_id)

                 //   id.value.hospitalId = it.data.hospital?.id!!
                 //   userUseCases.setId(id.value)
                    "확인 ${id.value} and ${user.value}".log()
                    _eventFlow.emit(UiEvent.SaveDoctor)
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

    private suspend fun getUserId() {
        userUseCases.getId().collect() {
            id.value.userId = it.userId
        }
    }
    fun onEvent(event: DoctorEvent, doctor_id: Int){
        when (event) {
            is DoctorEvent.EnteredName -> {
                _user.value = user.value.copy(
                    name = event.value
                )
            }
            is DoctorEvent.EnteredMajor -> {
                _user.value = user.value.copy(
                    subjects = event.value
                )
            }
            is DoctorEvent.EnteredHospital -> {
                _user.value = user.value.copy(
                    hospital = event.value
                )
            }
            is DoctorEvent.SaveDoctor -> {
                viewModelScope.launch {
                    try {
                        if ( user.value.name.isBlank()
                            || user.value.subjects.isBlank()
                            || user.value.hospital == 0
                        ) {
                            _eventFlow.emit(UiEvent.ShowSnackbar("모든 칸의 내용을 채워주세요"))
                            "ERROR 입력 되지 않은 칸이 존재".log()
                            return@launch
                        }
                        putUserInfo(doctor_id)
                  //      _eventFlow.emit(UiEvent.SaveDoctor)
                    } catch (e: Exception) {
                        "의료진 저장 중 에러 발생".log()
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = "의료진 정보 저장 중 에러 발생"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDoctor : UiEvent()
    }


}
