package com.example.i_go.feature_note.data.repository

import com.example.i_go.feature_note.data.remote.UserAPI
import com.example.i_go.feature_note.data.remote.requestDTO.LoginPasswordDTO
import com.example.i_go.feature_note.data.remote.requestDTO.SignInDTO
import com.example.i_go.feature_note.data.remote.requestDTO.UserDTO
import com.example.i_go.feature_note.data.remote.responseDTO.SignInResponseDTO
import com.example.i_go.feature_note.data.storage.TokenStore
import com.example.i_go.feature_note.domain.model.Token
import com.example.i_go.feature_note.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val store: TokenStore,
    private val api: UserAPI
) : UserRepository {
    override suspend fun doLogin(loginPasswordDTO: LoginPasswordDTO): Response<Token> {
        return api.login(loginPasswordDTO)
    }
    override suspend fun doSignIn(signInDTO: SignInDTO): Response<SignInResponseDTO> {
        return api.signIn(signInDTO)
    }

    override suspend fun getUserInfo(): Response<UserDTO> {
        return api.getUserInfo()
    }

    override fun getUserToken(): Token {
        return runBlocking(Dispatchers.IO) {
            store.getToken().first()
        }
    }

    override fun setUserToken(token: Token) {
        runBlocking(Dispatchers.IO) {
            store.setToken(token)
        }
    }
}