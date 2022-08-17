package com.example.i_go.feature_note.domain.use_case

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val doLogin: DoLogin,
    val getUserInfo: GetUserInfo,
    val getToken: GetToken,
    val setToken: SetToken
)
