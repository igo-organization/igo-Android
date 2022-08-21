package com.example.i_go.feature_note.presentation.doctors

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.UserResponseDTO
import com.example.i_go.feature_note.domain.model.ID
import com.example.i_go.feature_note.domain.model.Token
import com.example.i_go.feature_note.domain.use_case.user.PutUserInfo
import com.example.i_go.feature_note.domain.use_case.user.UserUseCases
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.doctors.hospitals.HospitalState
import com.example.i_go.feature_note.presentation.login.LoginViewModel
import com.example.i_go.feature_note.presentation.patients.PatientsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DoctorState())
    val state: State<DoctorState> = _state

   // private var userResponseDTO = mutableStateOf(UserResponseDTO())

    private var id = mutableStateOf(ID())

   // private var _doctor = mutableStateOf(UserDTO())
   // var doctor: State<UserDTO> = _doctor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            setUserId()
            setUserInfo(id.value.userId)
        }
    }

    fun onEvent(event: DoctorEvent) {
        when (event) {
            is DoctorEvent.EnteredName -> {
                _state.value.userDTO = state.value.userDTO.copy(
                    name = event.value
                )
            }
            is DoctorEvent.EnteredMajor -> {
                _state.value.userDTO = state.value.userDTO.copy(
                    subjects = event.value
                )
            }
            is DoctorEvent.EnteredHospital -> {
                _state.value.userDTO = state.value.userDTO.copy(
                    hospital = event.value
                )
            }
            is DoctorEvent.SaveDoctor -> {
                viewModelScope.launch {
                    try {
                        if ( state.value.userDTO.name.isBlank()
                            || state.value.userDTO.subjects.isBlank()
                            || state.value.userDTO.hospital != 0
                        ) {
                            _eventFlow.emit(UiEvent.ShowSnackbar("모든 칸의 내용을 채워주세요"))
                            "ERROR 입력 되지 않은 칸이 존재".log()
                            return@launch
                        }
                        putUserInfo()
                    } catch (e: Exception) {
                        "의료진 저장 중 에러 발생".log()
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = "의료진 정보 저장 중 에러 발생"))
                    }
                }
            }
        }
    }

    private suspend fun putUserInfo(){
        userUseCases.putUserInfo(
            doctor_id = id.value.userId,
            userDTO = state.value.userDTO
        ).collectLatest {
            when (it) {
                is Resource.Success -> {
                    state.value.userDTO = it.data!!
                    userUseCases.getUserInfo(id.value.userId)

                    id.value.hospitalId = it.data.hospital
                    userUseCases.setId(id.value)


                    _eventFlow.emit(UiEvent.SaveDoctor)
                    "의료진 정보 올리기 성공 happy ID: ${it.data} and ${id.value}".log()
                }
                is Resource.Error -> {
                    "의료진 정보 올리기 실패".log()
                    _eventFlow.emit(UiEvent.ShowSnackbar("실패!"))
                }
                is Resource.Loading -> "의료진 정보 올리는 중".log()

            }
        }
    }


    fun setUserInfo(user_id: Int) {
        viewModelScope.launch {
            userUseCases.getUserInfo(user_id).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = result.data?.let { DoctorState(userResponseDTO = it)}!!
                        Log.d("test", "success : ${result.data}")
                    }
                    is Resource.Error -> {
                        "망".log()
                        Log.d("test", "error")
                    }
                    is Resource.Loading -> {
                        "로딩중".log()
                        Log.d("test", "loading")
                    }
                }
            }
        }
    }

    suspend fun setUserId(){
        userUseCases.getId().collect() {
            id.value.userId = it.userId
        }
        "아이디는 ${id.value}".log()
    }



    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDoctor : UiEvent()
    }
}



