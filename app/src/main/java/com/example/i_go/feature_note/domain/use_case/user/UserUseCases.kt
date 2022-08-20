package com.example.i_go.feature_note.domain.use_case.user

import com.example.i_go.feature_note.domain.use_case.user.*
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val doLogin: DoLogin,
    val doSignIn: DoSignIn,
    val getUserInfo: GetUserInfo,
    val getToken: GetToken,
    val setToken: SetToken,
    val getHospitals: GetHospitals,
    val getId: GetId,
    val setId: SetId
)
