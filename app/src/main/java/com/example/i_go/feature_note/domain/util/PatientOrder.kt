package com.example.i_go.feature_note.domain.util

sealed class PatientOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): PatientOrder(orderType)
    class Date(orderType: OrderType): PatientOrder(orderType)
    class Image(orderType: OrderType): PatientOrder(orderType)
    class Diseases(orderType: OrderType): PatientOrder(orderType)

    fun copy(orderType: OrderType): PatientOrder {
        return when(this) {
            is Name -> Name(orderType)
            is Date -> Date(orderType)
            is Image -> Image(orderType)
            is Diseases -> Diseases(orderType)
        }
    }
}
