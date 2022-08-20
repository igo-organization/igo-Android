package com.example.i_go.feature_note.presentation.doctors

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_go.feature_note.domain.model.ID
import com.example.i_go.feature_note.domain.use_case.user.UserUseCases
import com.example.i_go.feature_note.domain.util.Resource
import com.example.i_go.feature_note.domain.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DoctorState())
    val state: State<DoctorState> = _state

    private var id = mutableStateOf(ID())

    init {
        getUserInfo(0)
    }
    var user_id = userUseCases.getId()

    fun onEvent(event: DoctorEvent) {
        when (event) {
            is DoctorEvent.EnteredName -> {

            }
            /*
            is DoctorEvent.EnteredMajor -> {
                _emailPw.value = emailPw.value.copy(
                    password = event.value
                )
            }
            is DoctorEvent.EnteredHospital -> {
                _emailPw.value = emailPw.value.copy(
                    password = event.value
                )
            }
            is DoctorEvent.EnteredPassword -> {
                _emailPw.value = emailPw.value.copy(
                    password = event.value
                )
            }
            is DoctorEvent.Login -> {
                viewModelScope.launch {
                    try {
                        if (emailPw.value.username.isNullOrBlank()
                            || emailPw.value.password.isNullOrBlank()
                        ) {
                            "ERROR 입력 되지 않은 칸이 존재".log()
                            _eventFlow.emit(LoginViewModel.UiEvent.Error(message = "모든 칸의 내용을 채워주세요"))
                            return@launch
                        }
                        login(scaffoldState)
                    } catch (e: Exception) {
                        "로그인 중 에러 발생 2".log()
                        scaffoldState.snackbarHostState.showSnackbar("로그인 실패")
                        _eventFlow.emit(LoginViewModel.UiEvent.Error(message = "로그인 중 에러 발생"))
                    }
                }
            }*/
        }

    }

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
}



