package com.example.i_go.feature_note.domain.model


class Doctor(
    var name: String,
    var major: String,
    var facility: String
)
class InvalidNoteException2(message: String): Exception(message)