package com.example.i_go.feature_note.domain.use_case.user

import com.example.i_go.feature_note.domain.model.Token
import com.example.i_go.feature_note.domain.repository.UserRepository
import com.example.i_go.feature_note.domain.util.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetToken @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(): Flow<Token> = flow {
        try {
            emit(repository.getUserToken())
        } catch(e: Exception) {
            "ERRROR: usecase GetToken form dataStore".log()
            emit(Token("", 0))
        }
    }
}